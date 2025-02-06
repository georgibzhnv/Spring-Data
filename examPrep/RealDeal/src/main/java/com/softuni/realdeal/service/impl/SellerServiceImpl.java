package com.softuni.realdeal.service.impl;

import com.softuni.realdeal.domain.dtos.imprt.SellerImportDto;
import com.softuni.realdeal.domain.dtos.imprt.SellerRootImportDto;
import com.softuni.realdeal.domain.entities.Rating;
import com.softuni.realdeal.domain.entities.Seller;
import com.softuni.realdeal.domain.repository.SellerRepository;
import com.softuni.realdeal.service.SellerService;
import com.softuni.realdeal.utils.ValidatorUtil;
import com.softuni.realdeal.utils.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private final static String SELLERS_PATH="src/main/resources/xml/sellers.xml";

    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count()>0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(SELLERS_PATH)));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();
        SellerRootImportDto sellerRootImportDto = this.xmlParser.parseXml(SellerRootImportDto.class, SELLERS_PATH);
        for (SellerImportDto sellerImportDto : sellerRootImportDto.getSellerImportDtos()) {
            Rating rating;
            try {
                rating=(Rating.valueOf(sellerImportDto.getRating()));
            }catch (Exception e){
                sb.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            Optional<Seller>byEmail = this.sellerRepository.findByEmail(sellerImportDto.getEmail());
//
            if (this.validatorUtil.isValid(sellerImportDto) && byEmail.isEmpty()){
                Seller seller = this.modelMapper.map(sellerImportDto,Seller.class);
                seller.setRating(rating);

                this.sellerRepository.saveAndFlush(seller);
                sb.append(String.format("Successfully imported sellers %s - %s",seller.getLastName(),seller.getEmail()))
                        .append(System.lineSeparator());
            }else {
                sb.append("Invalid seller").append(System.lineSeparator());
            }
        }

        return sb.toString();
    }
}

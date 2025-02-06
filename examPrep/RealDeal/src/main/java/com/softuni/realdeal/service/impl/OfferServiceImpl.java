package com.softuni.realdeal.service.impl;

import com.softuni.realdeal.domain.dtos.imprt.OfferImportDto;
import com.softuni.realdeal.domain.dtos.imprt.OfferImportRootDto;
import com.softuni.realdeal.domain.entities.Car;
import com.softuni.realdeal.domain.entities.Offer;
import com.softuni.realdeal.domain.entities.Seller;
import com.softuni.realdeal.domain.repository.CarRepository;
import com.softuni.realdeal.domain.repository.OfferRepository;
import com.softuni.realdeal.domain.repository.SellerRepository;
import com.softuni.realdeal.service.OfferService;
import com.softuni.realdeal.utils.ValidatorUtil;
import com.softuni.realdeal.utils.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

@Service
public class OfferServiceImpl implements OfferService {

    private final static String OFFERS_PATH="src/main/resources/xml/offers.xml";
    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil, CarRepository carRepository, SellerRepository sellerRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count()>0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(OFFERS_PATH)));
    }

    @Override
    public String importOffers() throws JAXBException {
        StringBuilder sb = new StringBuilder();
        OfferImportRootDto offerImportRootDto = this.xmlParser.parseXml(OfferImportRootDto.class, OFFERS_PATH);
        for (OfferImportDto offerImportDto : offerImportRootDto.getOffers()) {
            if (this.validatorUtil.isValid(offerImportDto)){
                Offer offer = this.modelMapper.map(offerImportDto,Offer.class);

                Car car = this.carRepository.findById(offerImportDto.getCar().getId()).get();
                Seller seller = this.sellerRepository.findById(offerImportDto.getSeller().getId()).get();


                offer.setPictures(new HashSet<>(car.getPictures()));
                offer.setCar(car);
                offer.setSeller(seller);

                this.offerRepository.saveAndFlush(offer);
                sb.append(String.format("Successfully imported offer %s - %s",offer.getAddedOn(),offer.isHasGoldStatus()))
                        .append(System.lineSeparator());
            }else {
            sb.append("Invalid offer.").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}

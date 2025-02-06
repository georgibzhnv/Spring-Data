package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.imprt.PartImportDto;
import com.softuni.json.domain.dto.imprt.PartImportRootDto;
import com.softuni.json.domain.entity.Part;
import com.softuni.json.domain.entity.Supplier;
import com.softuni.json.domain.repository.PartRepository;
import com.softuni.json.domain.repository.SupplierRepository;
import com.softuni.json.service.PartService;
import com.softuni.json.utils.ValidatorUtil;
import com.softuni.json.utils.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final static String PARTS_PATH="C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\parts.xml";
    private final SupplierRepository supplierRepository;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, ModelMapper modelMapper1, SupplierRepository supplierRepository, ValidatorUtil validatorUtil, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper1;
        this.supplierRepository = supplierRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedParts() throws Exception {
        PartImportRootDto partImportRootDto = this.xmlParser.parseXml(PartImportRootDto.class, PARTS_PATH);
        for (PartImportDto partDto : partImportRootDto.getParts()) {
            Part part= this.modelMapper.map(partDto,Part.class);
            part.setSupplier(this.getRandomSupplier());

            this.partRepository.saveAndFlush(part);
        }
    }

    private Supplier getRandomSupplier() throws Exception {
        Random random=new Random();

        long index = (long) random.nextInt((int) this.supplierRepository.count())+1;
        Optional<Supplier>supplier = this.supplierRepository.findById(index);

        if (supplier.isPresent()){
            return supplier.get();
        }else {
            throw new Exception("Supplier don't exist.");
        }
    }
}

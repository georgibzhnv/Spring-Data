package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.imprt.PartSeedDto;
import com.softuni.json.domain.entity.Part;
import com.softuni.json.domain.entity.Supplier;
import com.softuni.json.domain.repository.PartRepository;
import com.softuni.json.domain.repository.SupplierRepository;
import com.softuni.json.service.PartService;
import com.softuni.json.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final static String PARTS_PATH="src/main/resources/jsons/parts.json";
    private final SupplierRepository supplierRepository;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, ModelMapper modelMapper, Gson gson, SupplierRepository supplierRepository, ValidatorUtil validatorUtil) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.supplierRepository = supplierRepository;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void seedParts() throws Exception {
        String content= String.join("", Files
                .readAllLines(Path.of(PARTS_PATH)));

        PartSeedDto[] partSeedDtos = this.gson.fromJson(content,PartSeedDto[].class);
        for (PartSeedDto partSeedDto : partSeedDtos) {
           if (this.validatorUtil.isValid(partSeedDto)) {
               Part part = this.modelMapper.map(partSeedDto, Part.class);
               part.setSupplier(getRandomSupplier());
               this.partRepository.saveAndFlush(part);
           }else {
           }
        }
    }

    private Supplier getRandomSupplier() throws Exception {
        Random random=new Random();
        long index = (long)random.nextInt((int)this.supplierRepository.count())+1;
        Optional<Supplier> supplier = this.supplierRepository.findById(index);

        if (supplier.isPresent()){
            return supplier.get();
        }
        else {
            throw new Exception("Supplier doesn't exist.");
        }
    }
}

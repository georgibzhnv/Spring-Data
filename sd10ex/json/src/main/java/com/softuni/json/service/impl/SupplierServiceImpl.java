package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.SupplierExportDto;
import com.softuni.json.domain.dto.imprt.SupplierImportDto;
import com.softuni.json.domain.dto.imprt.SupplierRootImportDto;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.entity.Supplier;
import com.softuni.json.domain.repository.SupplierRepository;
import com.softuni.json.service.SupplierService;
import com.softuni.json.utils.XmlParser;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final static String SUPPLIER_PATH="C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\suppliers.xml";

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedSuppliers() throws IOException,JAXBException {
        SupplierRootImportDto supplierRootImportDto = this.xmlParser.parseXml(SupplierRootImportDto.class, SUPPLIER_PATH);
        for (SupplierImportDto supplier : supplierRootImportDto.getSuppliers()) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplier,Supplier.class));
        }
    }
}

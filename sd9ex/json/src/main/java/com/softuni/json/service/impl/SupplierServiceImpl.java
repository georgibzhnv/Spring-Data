package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.SupplierExportDto;
import com.softuni.json.domain.dto.imprt.SupplierSeedDto;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.entity.Supplier;
import com.softuni.json.domain.repository.SupplierRepository;
import com.softuni.json.service.SupplierService;
import jakarta.transaction.Transactional;
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
    private final static String SUPPLIER_PATH="src/main/resources/jsons/suppliers.json";
    private final Gson gson;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSuppliers() throws IOException {
        String content= String.join("",Files
                .readAllLines(Path.of(SUPPLIER_PATH)));

        SupplierSeedDto[] supplierSeedDtos = this.gson.fromJson(content, SupplierSeedDto[].class);
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplierSeedDto, Supplier.class));
        }
    }

    @Transactional
    @Override
    public String printNotImporters() {
        Set<Supplier> suppliers = this.supplierRepository.findByImporterFalse();
        List<SupplierExportDto>toExport = new ArrayList<>();
        for (Supplier supplier: suppliers) {
            SupplierExportDto supplierExportDto = this.modelMapper.map(supplier,SupplierExportDto.class);
            supplierExportDto.setCount(supplier.getParts().size());
            toExport.add(supplierExportDto);
        }
        return gson.toJson(toExport);
    }
}

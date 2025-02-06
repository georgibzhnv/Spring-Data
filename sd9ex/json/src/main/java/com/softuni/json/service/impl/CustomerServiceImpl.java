package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.CustomerExportDto;
import com.softuni.json.domain.dto.imprt.CustomerSeedDto;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.CustomerRepository;
import com.softuni.json.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final static String CUSTOMERS_PATH="src/main/resources/jsons/customers.json";
    private final Gson gson;
    private final CarRepository carRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, CarRepository carRepository) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.carRepository = carRepository;
    }

    @Override
    public void seedCustomers() throws IOException {
        String content = String.join("",Files.readAllLines(Path.of(CUSTOMERS_PATH)));

        CustomerSeedDto[] customerSeedDtos=this.gson.fromJson(content, CustomerSeedDto[].class);
        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Customer customer = this.modelMapper.map(customerSeedDto,Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public String orderedCustomers() {
        Set<Customer>orderedCustomers=this.customerRepository.getAllByOrderByYoungDriverAscBirthDateAsc();
        List<CustomerExportDto> toExport = new ArrayList<>();
        for (Customer orderedCustomer : orderedCustomers) {
            CustomerExportDto customerExportDto = this.modelMapper.map(orderedCustomer,CustomerExportDto.class);
            toExport.add(customerExportDto);
        }
        return this.gson.toJson(toExport);
    }
}

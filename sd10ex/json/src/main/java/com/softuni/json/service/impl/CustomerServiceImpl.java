package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.CustomerOrderedRootDto;
import com.softuni.json.domain.dto.export.CustomerOrderedExportDto;
import com.softuni.json.domain.dto.imprt.CustomerImportDto;
import com.softuni.json.domain.dto.imprt.CustomerImportRootDto;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.CustomerRepository;
import com.softuni.json.service.CustomerService;
import com.softuni.json.utils.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final String CUSTOMERS_ORDERED_PATH = "C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\exported\\ordered-customers.xml";

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final static String CUSTOMERS_PATH="C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\customers.xml";
    private final CarRepository carRepository;
    private final XmlParser xmlParser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, CarRepository carRepository, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws JAXBException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser.parseXml(CustomerImportRootDto.class, CUSTOMERS_PATH);

        for (CustomerImportDto customerDto : customerImportRootDto.getCustomers()) {
            Customer customer = this.modelMapper.map(customerDto,Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public void exportOrderedCustomers() throws JAXBException {
        List<CustomerOrderedExportDto> dtos = this.customerRepository.findAllAndSort()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedExportDto.class))
                .collect(Collectors.toList());

        CustomerOrderedRootDto rootDto = new CustomerOrderedRootDto();
        rootDto.setCustomers(dtos);

        this.xmlParser.exportXml(rootDto, CustomerOrderedRootDto.class,CUSTOMERS_ORDERED_PATH);
    }
}

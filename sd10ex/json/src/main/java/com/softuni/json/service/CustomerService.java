package com.softuni.json.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CustomerService {
    void seedCustomers() throws JAXBException;
    void exportOrderedCustomers() throws JAXBException;
}

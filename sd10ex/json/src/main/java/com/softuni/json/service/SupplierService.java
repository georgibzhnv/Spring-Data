package com.softuni.json.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SupplierService {

    void seedSuppliers() throws IOException, JAXBException;
}

package com.softuni.json.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CarService {
    void seedCars() throws Exception;
    void carsAndParts() throws JAXBException;
}

package com.softuni.json.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CarService {
    void seedCars() throws Exception;

    String findByMake();
}

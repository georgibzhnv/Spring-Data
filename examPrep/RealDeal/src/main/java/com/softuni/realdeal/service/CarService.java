package com.softuni.realdeal.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CarService {

    boolean areImported();
    String readCarsFileContent() throws IOException;
    String importCars() throws IOException;
    String getCarsOrderByPictureCountThenByMake();
//    String exportSoldCars();
}

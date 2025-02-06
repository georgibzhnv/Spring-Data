package com.softuni.realdeal.service.impl;

import com.google.gson.Gson;
import com.softuni.realdeal.domain.dtos.imprt.CarImportDto;
import com.softuni.realdeal.domain.entities.Car;
import com.softuni.realdeal.domain.repository.CarRepository;
import com.softuni.realdeal.service.CarService;
import com.softuni.realdeal.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_PATH="src/main/resources/json/cars.json";

    private final CarRepository carRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private  final ValidatorUtil validatorUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count()>0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(CARS_PATH)));
    }

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        CarImportDto[] carImportDtos = this.gson.fromJson(this.readCarsFileContent(), CarImportDto[].class);
        for (CarImportDto carImportDto : carImportDtos) {
            if(this.validatorUtil.isValid(carImportDto)){
                this.carRepository.saveAndFlush(this.modelMapper.map(carImportDto, Car.class));

                sb.append(String
                        .format("Succesfully imported car - %s - %s",carImportDto.getMake(),carImportDto.getModel()))
                        .append(System.lineSeparator());
            }else {
                sb.append("Invalid car").append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public String getCarsOrderByPictureCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        Set<Car> cars = this.carRepository.exportCars();

        for (Car car : cars) {
            sb
                    .append(String.format("Car make - %s, model - %s",car.getMake(),car.getModel())).append(System.lineSeparator())
                    .append(String.format("\tKilometers - %s",car.getKilometers())).append(System.lineSeparator())
                    .append(String.format("\tRegistered on - %s",car.getRegisteredOn())).append(System.lineSeparator())
                    .append(String.format("\tNumbers of pictures - %d",car.getPictures().size())).append(System.lineSeparator());
        }
        return sb.toString();
    }
}

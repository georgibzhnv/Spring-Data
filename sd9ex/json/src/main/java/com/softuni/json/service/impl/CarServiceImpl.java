package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.CarExportDto;
import com.softuni.json.domain.dto.imprt.CarSeedDto;
import com.softuni.json.domain.entity.Car;
import com.softuni.json.domain.entity.Part;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.PartRepository;
import com.softuni.json.service.CarService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final PartRepository partRepository;
    private final static String CARS_PATH="src/main/resources/jsons/cars.json";

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, PartRepository partRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.partRepository = partRepository;
    }

    @Transactional
    @Override
    public void seedCars() throws Exception {
        String content = String.join("", Files
                .readAllLines(Path.of(CARS_PATH)));

        CarSeedDto[] carSeedDtos = this.gson.fromJson(content, CarSeedDto[].class);
        for (CarSeedDto carSeedDto : carSeedDtos) {
            Car car  = this.modelMapper.map(carSeedDto,Car.class);
            car.setParts(getRandomParts());

            this.carRepository.saveAndFlush(car);
        }
    }

    @Override
    public String findByMake() {
        Set<Car> toyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");
        List<CarExportDto>carExportDtos = new ArrayList<>();
        for (Car car : toyota) {
            CarExportDto carExportDto=this.modelMapper.map(car,CarExportDto.class);
            carExportDtos.add(carExportDto);
        }
        return this.gson.toJson(carExportDtos);
    }

    private Set<Part> getRandomParts() throws Exception {
        Set<Part>parts = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long index = (long)random.nextInt((int)this.partRepository.count())+1;
        Optional<Part> part = this.partRepository.findById(index);
        if (part.isPresent()){
            return part.get();
        }else {
            throw new Exception("Invalid part ID.");
        }
    }
}

package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.CarExportDto;
import com.softuni.json.domain.dto.export.CarExportRootDto;
import com.softuni.json.domain.dto.export.PartExportDto;
import com.softuni.json.domain.dto.export.PartExportRootDto;
import com.softuni.json.domain.dto.imprt.CarImportDto;
import com.softuni.json.domain.dto.imprt.CarImportRootDto;
import com.softuni.json.domain.entity.Car;
import com.softuni.json.domain.entity.Part;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.PartRepository;
import com.softuni.json.service.CarService;
import com.softuni.json.utils.XmlParser;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CarServiceImpl implements CarService {

    private final static String CARS_AND_PARTS_PATH = "C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\exported\\cars-and-parts.xml";

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartRepository partRepository;
    private final static String CARS_PATH="C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\cars.xml";
    private final XmlParser xmlParser;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, PartRepository partRepository, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {
        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, CARS_PATH);
        for (CarImportDto carDto : carImportRootDto.getCars()) {
            Car car = this.modelMapper.map(carDto,Car.class);
            car.setParts(this.getRandomParts());

            this.carRepository.saveAndFlush(car);
        }
    }

    @Override
    public void carsAndParts() throws JAXBException {
        List<Car> cars = this.carRepository.findAll();
        CarExportRootDto carRootDto = new CarExportRootDto();
        List<CarExportDto>carExportDtos = new ArrayList<>();

        for (Car car : cars) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
            carExportDtos.add(carExportDto);
        }
       carRootDto.setCars(carExportDtos);
        this.xmlParser.exportXml(carRootDto,CarExportRootDto.class,CARS_AND_PARTS_PATH);
    }

    private Set<Part> getRandomParts() throws Exception{
        Set<Part>parts = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            Part part = this.getRandomPart();
            parts.add(part);
        }
        return parts;
    }

    private Part getRandomPart() throws Exception{
        Random random=new Random();
        long index = (long)random.nextInt((int)this.partRepository.count())+1;
        Optional<Part>part = this.partRepository.findById(index);

        if (part.isPresent()){
            return part.get();
        }else {
            throw new Exception("Part does not exist.");
        }
    }
}

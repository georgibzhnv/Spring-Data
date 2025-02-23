package com.softuni.realdeal.service.impl;

import com.google.gson.Gson;
import com.softuni.realdeal.domain.dtos.imprt.PictureImportDto;
import com.softuni.realdeal.domain.entities.Picture;
import com.softuni.realdeal.domain.repository.CarRepository;
import com.softuni.realdeal.domain.repository.PictureRepository;
import com.softuni.realdeal.service.PictureService;
import com.softuni.realdeal.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {

    private final static String PICTURES_PATH="src/main/resources/json/pictures.json";

    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final CarRepository carRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil, CarRepository carRepository) {
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.carRepository = carRepository;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count()>0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PICTURES_PATH)));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();
        PictureImportDto[] pictureImportDtos = this.gson.fromJson(this.readPicturesFromFile(), PictureImportDto[].class);

        for (PictureImportDto pictureImportDto : pictureImportDtos) {

            Optional<Picture> byName =this.pictureRepository.findByName(pictureImportDto.getName());
            if (this.validatorUtil.isValid(pictureImportDto) && byName.isEmpty()){
                Picture picture = this.modelMapper.map(pictureImportDto,Picture.class);
                picture.setCar(this.carRepository.getOne(pictureImportDto.getCar()));

                this.pictureRepository.saveAndFlush(picture);
                sb.append(String.format("Successfully import picture - %s\n",pictureImportDto.getName()));
            }else {
                sb.append("Invalid picture%n");
            }
        }
        return sb.toString();
    }
}

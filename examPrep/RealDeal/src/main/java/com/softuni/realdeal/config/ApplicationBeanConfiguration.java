package com.softuni.realdeal.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.softuni.realdeal.utils.ValidatorUtil;
import com.softuni.realdeal.utils.ValidatorUtilImpl;
import com.softuni.realdeal.utils.XmlParser;
import com.softuni.realdeal.utils.XmlParserImpl;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<Object>)
                        (json, type, jsonDeserializationContext) -> {
                    return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                })
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<Object>)
                        (json, type, jsonDeserializationContext) -> {
                            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                        })
                .create();
    }
    @Bean
    public XmlParser xmlParser(){
        return new XmlParserImpl();
    }

    @Bean
    public Validator validator(){
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
    @Bean
    public ValidatorUtil validatorUtil(){
        return new ValidatorUtilImpl(validator());
    }

}

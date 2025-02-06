package com.softuni.realdeal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.softuni.realdeal.domain.entities")
public class RealDealApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealDealApplication.class, args);
    }

}

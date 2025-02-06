package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.entity.Car;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.entity.Sale;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.CustomerRepository;
import com.softuni.json.domain.repository.SaleRepository;
import com.softuni.json.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public void seedSales() throws Exception {
        Sale sale = new Sale();
        sale.setCustomer(getRandomCustomer());
        sale.setCar(getRandomCar());
        sale.setDiscount(5);

        Sale sale1 = new Sale();
        sale1.setCustomer(getRandomCustomer());
        sale1.setCar(getRandomCar());
        sale1.setDiscount(10);

        Sale sale2 = new Sale();
        sale2.setCustomer(getRandomCustomer());
        sale2.setCar(getRandomCar());
        sale2.setDiscount(30);

        this.saleRepository.saveAndFlush(sale);
        this.saleRepository.saveAndFlush(sale1);
        this.saleRepository.saveAndFlush(sale2);
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.customerRepository.count())+1;
        return this.customerRepository.findById(id).get();
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.carRepository.count())+1;
        return this.carRepository.findById(id).get();
    }
}

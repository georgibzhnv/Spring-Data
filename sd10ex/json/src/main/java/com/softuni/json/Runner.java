package com.softuni.json;

import com.softuni.json.service.*;
import com.softuni.json.service.impl.CustomerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerServiceImpl customerService;
    private final SaleService saleService;

    public Runner(SupplierService supplierService, PartService partService, CarService carService, CustomerServiceImpl customerService, SaleService saleService) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.supplierService.seedSuppliers();
//        this.partService.seedParts();
//        this.carService.seedCars();
//        this.customerService.seedCustomers();
//        this.saleService.seedSales();

//        this.customerService.exportOrderedCustomers();
//        this.carService.carsAndParts();
        this.saleService.saleDiscounts();
    }
}

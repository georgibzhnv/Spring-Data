package com.softuni.realdeal.web.controllers;

import com.softuni.realdeal.service.CarService;
import com.softuni.realdeal.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController{
    private CarService carService;
    private SellerService sellerService;

    @Autowired
    public ExportController(CarService carService, SellerService sellerService) {
        this.carService = carService;
        this.sellerService = sellerService;
    }
//
//    @GetMapping("/cars-if-sold-xml")
//    public ModelAndView getAllCarsSoldXml(){
//       return new ModelAndView("export/export-cars-if-sold","carsIfSold" ,carService.exportSoldCars());
//    }
}

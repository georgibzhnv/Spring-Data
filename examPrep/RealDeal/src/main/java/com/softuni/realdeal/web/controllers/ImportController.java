package com.softuni.realdeal.web.controllers;

import com.softuni.realdeal.service.CarService;
import com.softuni.realdeal.service.OfferService;
import com.softuni.realdeal.service.PictureService;
import com.softuni.realdeal.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController{

    private final CarService carService;
    private final OfferService offerService;
    private final PictureService pictureService;
    private final SellerService sellerService;

    @Autowired
    public ImportController(CarService carService, OfferService offerService, PictureService pictureService, SellerService sellerService) {
        this.carService = carService;
        this.offerService = offerService;
        this.pictureService = pictureService;
        this.sellerService = sellerService;
    }
//
//    @GetMapping("/json")
//    public ModelAndView importJson(){
//
//        boolean[] areImported = new boolean[]{
//                this.carService.areImported(),
//                this.pictureService.areImported()
//        };
//        return super.view("json/import-json","areImported",areImported);
//    }
}

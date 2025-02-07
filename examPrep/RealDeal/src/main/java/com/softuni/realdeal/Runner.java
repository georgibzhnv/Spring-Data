package com.softuni.realdeal;

import com.softuni.realdeal.service.CarService;
import com.softuni.realdeal.service.OfferService;
import com.softuni.realdeal.service.PictureService;
import com.softuni.realdeal.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final CarService carService;
    private final PictureService pictureService;
    private final SellerService sellerService;
    private final OfferService offerService;

    @Autowired
    public Runner(CarService carService, PictureService pictureService, SellerService sellerService, OfferService offerService) {
        this.carService = carService;
        this.pictureService = pictureService;
        this.sellerService = sellerService;
        this.offerService = offerService;
    }

    @Override
    public void run(String... args) throws Exception {
//        System.out.println(this.carService.importCars());
//        System.out.println(this.pictureService.importPictures());
//        System.out.println(this.sellerService.importSellers());
//        System.out.println(this.offerService.importOffers());

//        System.out.println(this.carService.getCarsOrderByPictureCountThenByMake());
    }
}

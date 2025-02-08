package com.softuni.realdeal.web.controllers;

import com.softuni.realdeal.service.CarService;
import com.softuni.realdeal.service.OfferService;
import com.softuni.realdeal.service.PictureService;
import com.softuni.realdeal.service.SellerService;
import jakarta.xml.bind.JAXBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

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

    @GetMapping("/xml")
    public ModelAndView xml(){
        ModelAndView modelAndView = super.view("xml/import-xml");
        boolean[] areImported=new boolean[]{this.carService.areImported(),this.offerService.areImported(),
                this.pictureService.areImported(),this.sellerService.areImported()};
        modelAndView.addObject("areImported",areImported);
        return modelAndView;
    }

    @GetMapping("/cars")
    public ModelAndView cars() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-cars");
        modelAndView.addObject("cars",this.carService.readCarsFileContent());
        return modelAndView;
    }

    @PostMapping("/cars")
    public  ModelAndView carsConfirm() throws IOException {
        this.carService.importCars();
        return super.redirect("/import/xml");
    }
    @GetMapping("/offers")
    public ModelAndView offers() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-offers");
        modelAndView.addObject("offers",this.offerService.readOffersFileContent());
        return modelAndView;
    }

    @PostMapping("/offers")
    public  ModelAndView offersConfirm() throws IOException, JAXBException {
        this.offerService.importOffers();
        return super.redirect("/import/xml");
    }
    
    @GetMapping("/pictures")
    public ModelAndView pictures() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-pictures");
        modelAndView.addObject("pictures",this.pictureService.readPicturesFromFile());
        return modelAndView;
    }

    @PostMapping("/pictures")
    public  ModelAndView picturesConfirm() throws IOException, JAXBException {
        this.pictureService.importPictures();
        return super.redirect("/import/xml");
    }
    
    @GetMapping("/sellers")
    public ModelAndView sellers() throws IOException {
        ModelAndView modelAndView = super.view("xml/import-sellers");
        modelAndView.addObject("sellers",this.sellerService.readSellersFromFile());
        return modelAndView;
    }

    @PostMapping("/sellers")
    public  ModelAndView sellersConfirm() throws IOException, JAXBException {
        this.sellerService.importSellers();
        return super.redirect("/import/xml");
    }


}

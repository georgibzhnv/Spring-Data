package com.softuni.realdeal.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController{

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView index(){
        return this.view("index");
    }
}

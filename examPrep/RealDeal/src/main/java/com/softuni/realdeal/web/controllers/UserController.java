package com.softuni.realdeal.web.controllers;

import com.softuni.realdeal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        return new ModelAndView("user/register");
    }


    @GetMapping("/login")
    public ModelAndView login(){
        return new ModelAndView("user/login");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(UserRegisterModel userRegisterModel){
        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmedPassword())){
            return super.redirect("/users/register");
        }
        this.userService.registerUser(userRegisterModel);
        return super.redirect("/users/login");
    }
}

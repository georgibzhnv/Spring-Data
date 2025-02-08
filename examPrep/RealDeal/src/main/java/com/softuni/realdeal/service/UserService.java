package com.softuni.realdeal.service;

import com.softuni.realdeal.service.models.UserServiceModel;
import com.softuni.realdeal.web.controllers.UserRegisterModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserRegisterModel userRegisterModel);
}

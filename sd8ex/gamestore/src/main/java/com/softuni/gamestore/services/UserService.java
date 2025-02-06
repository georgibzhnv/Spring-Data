package com.softuni.gamestore.services;

import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto loginDto);
    String logout();
}

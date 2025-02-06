package com.softuni.gamestore.services.impl;

import com.softuni.gamestore.domain.dtos.UserDto;
import com.softuni.gamestore.domain.dtos.UserLoginDto;
import com.softuni.gamestore.domain.dtos.UserRegisterDto;
import com.softuni.gamestore.domain.entities.Role;
import com.softuni.gamestore.domain.entities.User;
import com.softuni.gamestore.repositories.UserRepository;
import com.softuni.gamestore.services.GameService;
import com.softuni.gamestore.services.UserService;
import com.softuni.gamestore.utils.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserService {
    private final ValidatorUtil validatorUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private UserDto loggedUser;
    private final GameService gameService;


    @Autowired
    public UserServicesImpl(ValidatorUtil validatorUtil, UserRepository userRepository, ModelMapper modelMapper, GameService gameService) {
        this.validatorUtil = validatorUtil;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb=new StringBuilder();
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmedPassword())){
            sb.append("Password doesn't match");
        }else if (this.validatorUtil.isValid(userRegisterDto)){
            User user =this.modelMapper.map(userRegisterDto,User.class);

            if (this.userRepository.count()==0){
                user.setRole(Role.ADMIN);
            }else{
                user.setRole(Role.USER);
            }
            sb.append(String.format("%s was registered.%n",userRegisterDto.getFullName()));
            this.userRepository.saveAndFlush(user);
        }else {
            this.validatorUtil.violations(userRegisterDto)
                    .stream()
                    .forEach(e->sb.append(String.format("%s%n",e.getMessage())));
        }


        return sb.toString().trim();
    }

    @Override
    public String loginUser(UserLoginDto loginDto) {
        StringBuilder sb = new StringBuilder();
        Optional<User> user= this.userRepository
                .findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        if (user.isPresent()){
            if (this.loggedUser!=null){
                sb.append("User is already logged in.%n");
            }else {
                this.loggedUser=this.modelMapper.map(user.get(),UserDto.class);
                this.gameService.setLoggedUser(this.loggedUser);
                sb.append(String.format("Successfully logged in %s.%n",user.get().getFullName()));

            }
        }else {
            sb.append("Incorrect email/password%n");
        }
        return sb.toString();
    }

    @Override
    public String logout() {
        if (this.loggedUser==null){
            return "Cannot log out.No user was logged in.";
        }else {
            String message= String.format("User %s successfully logged out.",this.loggedUser.getFullName());
            this.loggedUser=null;
            return message;
        }
    }
}

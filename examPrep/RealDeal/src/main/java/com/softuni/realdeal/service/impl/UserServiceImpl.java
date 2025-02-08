package com.softuni.realdeal.service.impl;

import com.softuni.realdeal.domain.entities.User;
import com.softuni.realdeal.domain.repository.RoleRepository;
import com.softuni.realdeal.domain.repository.UserRepository;
import com.softuni.realdeal.service.RoleService;
import com.softuni.realdeal.service.UserService;
import com.softuni.realdeal.service.models.UserServiceModel;
import com.softuni.realdeal.web.controllers.UserRegisterModel;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.softuni.realdeal.domain.entities.User.*;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserServiceModel registerUser(UserRegisterModel userRegisterModel) {
        User user = this.modelMapper.map(userRegisterModel,User.class);

        if (this.userRepository.count()==0){
            this.roleService.seedRolesInDb();
            user.setAuthorities(new HashSet<>(this.roleRepository.findAll()));
        }else {
            user.setAuthorities(new HashSet<>());
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return this.modelMapper.map(this.userRepository.saveAndFlush(user),UserServiceModel.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }
}

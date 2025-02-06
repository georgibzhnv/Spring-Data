package com.softuni.jsondemo.service;

import com.softuni.jsondemo.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User>getAllUsers();
    User getUserById(Long id);
    User addUser(User user);
    User updateUser(User user);
    User deleteUser(Long id);
    long getUsersCount();


}

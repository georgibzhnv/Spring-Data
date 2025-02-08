package com.softuni.realdeal.service;

import com.softuni.realdeal.service.models.RoleServiceModel;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {

    void seedRolesInDb();

    Set<RoleServiceModel>findAllRoles();

    RoleServiceModel findByAuthority(String role);
}

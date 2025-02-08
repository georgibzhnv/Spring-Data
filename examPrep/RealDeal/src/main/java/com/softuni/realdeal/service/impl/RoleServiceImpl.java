package com.softuni.realdeal.service.impl;

import com.softuni.realdeal.domain.entities.Role;
import com.softuni.realdeal.domain.repository.RoleRepository;
import com.softuni.realdeal.service.RoleService;
import com.softuni.realdeal.service.models.RoleServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedRolesInDb() {
        Role root = new Role("ROOT");
        Role user = new Role("USER");

        this.roleRepository.saveAndFlush(root);
        this.roleRepository.saveAndFlush(user);
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll()
                .stream()
                .map(role -> this.modelMapper.map(role,RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String role) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(role),RoleServiceModel.class);
    }
}

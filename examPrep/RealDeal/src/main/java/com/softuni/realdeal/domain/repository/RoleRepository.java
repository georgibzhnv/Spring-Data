package com.softuni.realdeal.domain.repository;

import com.softuni.realdeal.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role findByAuthority(String role);
}

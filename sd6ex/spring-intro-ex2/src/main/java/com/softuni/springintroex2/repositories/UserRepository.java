package com.softuni.springintroex2.repositories;

import com.softuni.springintroex2.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}

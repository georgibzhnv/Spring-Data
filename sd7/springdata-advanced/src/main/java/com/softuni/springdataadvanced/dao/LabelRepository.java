package com.softuni.springdataadvanced.dao;

import com.softuni.springdataadvanced.entity.Label;
import com.softuni.springdataadvanced.entity.Shampoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label,Long> {
    Optional<Label> findByTitle(String title);
}

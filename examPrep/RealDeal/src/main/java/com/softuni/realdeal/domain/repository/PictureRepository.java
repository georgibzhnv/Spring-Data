package com.softuni.realdeal.domain.repository;

import com.softuni.realdeal.domain.entities.Picture;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture,Integer> {
    Optional<Picture> findByName(@Length(min = 2,max = 20) String name);
}

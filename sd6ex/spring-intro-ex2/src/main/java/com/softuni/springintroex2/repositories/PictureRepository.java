package com.softuni.springintroex2.repositories;

import com.softuni.springintroex2.entities.Picture;
import com.softuni.springintroex2.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture,Long> {
    
}

package com.softuni.springintroex2.repositories;

import com.softuni.springintroex2.entities.Album;
import com.softuni.springintroex2.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album,Long> {
    
}

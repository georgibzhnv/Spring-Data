package com.softuni.realdeal.domain.repository;

import com.softuni.realdeal.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    @Query("SELECT c FROM Car c ORDER BY SIZE( c.pictures) DESC,c.make")
    Set<Car>exportCars();
}

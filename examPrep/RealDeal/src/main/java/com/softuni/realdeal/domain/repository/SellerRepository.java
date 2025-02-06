package com.softuni.realdeal.domain.repository;

import com.softuni.realdeal.domain.entities.Seller;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Integer> {
    Optional<Seller> findByEmail(@Pattern(regexp = "^(\\w+@\\w+)(.\\w+){2,}$") String email);
}

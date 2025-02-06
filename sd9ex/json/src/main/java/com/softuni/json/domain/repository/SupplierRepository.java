package com.softuni.json.domain.repository;

import com.softuni.json.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Set<Supplier> findByImporterFalse();
}

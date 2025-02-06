package com.softuni.json.domain.repository;

import com.softuni.json.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c from Customer c ORDER BY c.birthDate,c.youngDriver desc ")
    Set<Customer>findAllAndSort();
}

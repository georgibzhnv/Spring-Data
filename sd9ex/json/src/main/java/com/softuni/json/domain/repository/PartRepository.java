package com.softuni.json.domain.repository;

import com.softuni.json.domain.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part,Long> {

}

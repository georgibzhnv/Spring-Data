package com.softuni.mappingdto.dao;

import com.softuni.mappingdto.entity.Address;
import com.softuni.mappingdto.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

}

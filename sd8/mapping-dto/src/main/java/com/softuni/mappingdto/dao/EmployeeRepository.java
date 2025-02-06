package com.softuni.mappingdto.dao;

import com.softuni.mappingdto.entity.Employee;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
@Query("SELECT e from Employee e WHERE e.subordinates is not empty")
    List<Employee>getManagers();

    List<Employee> findAllByBirthdayBeforeOrderBySalaryDesc(@NonNull LocalDate date);
}

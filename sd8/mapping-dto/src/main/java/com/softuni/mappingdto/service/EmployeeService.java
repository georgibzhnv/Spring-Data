package com.softuni.mappingdto.service;

import com.softuni.mappingdto.entity.Employee;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface EmployeeService {
   List<Employee> getAllEmployees();
   List<Employee> getAllManagers();
   List<Employee> getAllEmployeesBornBefore(LocalDate toDate);
   Employee getEmployeeById(Long id);
   Employee addEmployee(Employee employee);
   Employee updateEmployee(Employee employee);
   Employee deleteEmployee(Long id);
   long getEmployeeCount();
}

package com.softuni.mappingdto.dto;

import com.softuni.mappingdto.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ManagerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String city;
    private List<EmployeeDto>employees;

    //Derived property
    private int getSubordinatesNumber(){
       return employees.size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(": ").append(firstName);
        sb.append(" ").append(lastName);
        sb.append(", City: ").append(city);
        sb.append(", Employees: ").append(getSubordinatesNumber()).append("\n");
        sb.append(employees.stream().map(emp->emp.toString()).collect(Collectors.joining("\n")));
        return sb.toString();
    }
}

package com.softuni.mappingdto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private LocalDate birthday;
    @NonNull
    private double salary;
    @ManyToOne
    @NonNull
    private Address address;
    private boolean onVacation;
    @ManyToOne(optional = true)
    @JoinColumn(name = "manager_id",
    referencedColumnName = "id")
    private Employee manager;

    @OneToMany(mappedBy = "manager",fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Employee> subordinates = new ArrayList<>();
}

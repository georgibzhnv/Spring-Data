package com.softuni.springdataadvanced.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "labels")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Label {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String title;
    private String subtitle;

    @ToString.Exclude
    @OneToMany(mappedBy = "label")
    private Set<Shampoo>shampoos = new HashSet<>();
}

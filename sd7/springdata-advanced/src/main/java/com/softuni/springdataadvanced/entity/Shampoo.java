package com.softuni.springdataadvanced.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shampoos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Shampoo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String brand;
    private double price;
    @Enumerated(EnumType.ORDINAL)
    private Size size;
    @ManyToOne
    private Label label;
    @ManyToMany(fetch = FetchType.EAGER,cascade = {PERSIST,REFRESH})
    @JoinTable(
            joinColumns = @JoinColumn(name="shampoo_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id",referencedColumnName = "id")
    )
    private Set<Ingredient>ingredients = new HashSet<>();
}

package com.softuni.json.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    private String make;
    private String model;
    private long travelledDistance;
    private Set<Part>parts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cars_parts",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns =@JoinColumn(name = "part_id"))
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    public Car() {
    }

    @Column
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name = "travelled_distance")
    public long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}

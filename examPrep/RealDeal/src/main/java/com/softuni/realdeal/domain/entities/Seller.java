package com.softuni.realdeal.domain.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity{

    private String firstName;
    private String lastName;
    private String email;
    private Rating rating;
    private String town;
    private Set<Offer> offer;

    @OneToMany(mappedBy = "seller")
    public Set<Offer> getOffer() {
        return offer;
    }

    public void setOffer(Set<Offer> offer) {
        this.offer = offer;
    }

    public Seller() {
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
    @Column(nullable = false)
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}

package com.softuni.springintroex2.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="towns")
public class Town extends BaseEntity{
    private String name;
    private String country;
    private Set<User>bornUsers;
    private Set<User>currentlyLivingUsers;

    @OneToMany(mappedBy = "bornTown",fetch = FetchType.EAGER)
    public Set<User> getBornUsers() {
        return bornUsers;
    }

    public void setBornUsers(Set<User> bornUsers) {
        this.bornUsers = bornUsers;
    }

    @OneToMany(mappedBy = "currentlyLiving",fetch = FetchType.EAGER)
    public Set<User> getCurrentlyLivingUsers() {
        return currentlyLivingUsers;
    }

    public void setCurrentlyLivingUsers(Set<User> currentlyLivingUsers) {
        this.currentlyLivingUsers = currentlyLivingUsers;
    }

    public Town() {
    }

    public Town(String name, String country) {
        this.name = name;
        this.country = country;
    }

    @Column(name = "name",nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="country",nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

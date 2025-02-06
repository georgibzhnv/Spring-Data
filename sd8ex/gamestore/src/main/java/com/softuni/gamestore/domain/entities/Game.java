package com.softuni.gamestore.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="games")
public class Game extends BaseEntity{
    private String title;
    private String trailer;
    private String imageThumbnail;
    private double size;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;
//    private Set<User> users;
//
//    @ManyToMany(mappedBy = "games")
//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    public Game() {
    }

    @Column(name = "title",nullable = false)
    public String getTitle() {
        return title;
    }


    @Column(name = "trailer")
    public String getTrailer() {
        return trailer;
    }


    @Column(name ="image_thumbnail")
    public String getImageThumbnail() {
        return imageThumbnail;
    }


    @Column(name = "size")
    public double getSize() {
        return size;
    }


    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }


    @Column(name = "description")
    public String getDescription() {
        return description;
    }


    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}

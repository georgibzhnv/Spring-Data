package com.softuni.realdeal.domain.dtos.imprt;

import com.softuni.realdeal.config.LocalDateTimeAdapter;
import jakarta.validation.constraints.DecimalMin;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportDto {

    @XmlElement
    private String description;
    @XmlElement
    private BigDecimal price;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "added-on")
    private LocalDateTime addedOn;
    @XmlElement(name = "has-gold-status")
    private boolean hasGoldStatus;
    @XmlElement
    private CarImportXMLDto car;
    @XmlElement
    private SellerImportXMLDto seller;

    public OfferImportDto() {
    }

    @Length(min = 5)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public boolean isHasGoldStatus() {
        return hasGoldStatus;
    }

    public void setHasGoldStatus(boolean hasGoldStatus) {
        this.hasGoldStatus = hasGoldStatus;
    }

    public CarImportXMLDto getCar() {
        return car;
    }

    public void setCar(CarImportXMLDto car) {
        this.car = car;
    }

    public SellerImportXMLDto getSeller() {
        return seller;
    }

    public void setSeller(SellerImportXMLDto seller) {
        this.seller = seller;
    }
}

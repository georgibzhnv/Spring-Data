package com.softuni.realdeal.domain.dtos.imprt;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferImportRootDto {

    @XmlElement(name = "offer")
    private List<OfferImportDto>offerImportDtos;

    public OfferImportRootDto() {
    }

    public List<OfferImportDto> getOffers() {
        return offerImportDtos;
    }

    public void setOffers(List<OfferImportDto> offers) {
        this.offerImportDtos = offers;
    }
}

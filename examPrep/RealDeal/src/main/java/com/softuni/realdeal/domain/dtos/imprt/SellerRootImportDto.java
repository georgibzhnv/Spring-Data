package com.softuni.realdeal.domain.dtos.imprt;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerRootImportDto {

    @XmlElement(name = "seller")
    private List<SellerImportDto> sellerImportDtos;


    public SellerRootImportDto() {
    }

    public List<SellerImportDto> getSellerImportDtos() {
        return sellerImportDtos;
    }

    public void setSellerImportDtos(List<SellerImportDto> sellerImportDtos) {
        this.sellerImportDtos = sellerImportDtos;
    }
}

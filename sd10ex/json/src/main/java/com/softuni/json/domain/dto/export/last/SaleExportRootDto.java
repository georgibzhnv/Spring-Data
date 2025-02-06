package com.softuni.json.domain.dto.export.last;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportRootDto {

    @XmlElement(name = "sale")
    private List<SaleExportDto>sales;

    public SaleExportRootDto() {
    }

    public List<SaleExportDto> getSales() {
        return sales;
    }

    public void setSales(List<SaleExportDto> sales) {
        this.sales = sales;
    }
}

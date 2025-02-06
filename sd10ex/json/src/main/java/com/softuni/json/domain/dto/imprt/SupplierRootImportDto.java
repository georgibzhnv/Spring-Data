package com.softuni.json.domain.dto.imprt;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name="suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierRootImportDto {

    @XmlElement(name = "supplier")
    private List<SupplierImportDto>suppliers;

    public SupplierRootImportDto() {
    }

    public List<SupplierImportDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierImportDto> suppliers) {
        this.suppliers = suppliers;
    }
}

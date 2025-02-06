package com.softuni.json.domain.dto.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedRootDto {

    @XmlElement(name = "customer")
    private List<CustomerOrderedExportDto>customers;

    public CustomerOrderedRootDto() {
    }

    public List<CustomerOrderedExportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerOrderedExportDto> customers) {
        this.customers = customers;
    }
}

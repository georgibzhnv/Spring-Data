package com.softuni.json.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

@Service
public interface SaleService {
    void seedSales() throws Exception;

    void saleDiscounts() throws JAXBException;
}

package com.softuni.realdeal.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SellerService {

    boolean areImported();
    String readSellersFromFile() throws IOException;
    String importSellers() throws IOException, JAXBException;
}

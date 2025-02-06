package com.softuni.realdeal.service;

import jakarta.xml.bind.JAXBException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface OfferService {
    boolean areImported();
    String readOffersFileContent() throws IOException;
    String importOffers() throws JAXBException;
}

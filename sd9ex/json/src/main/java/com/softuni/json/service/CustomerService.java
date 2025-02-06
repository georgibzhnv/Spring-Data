package com.softuni.json.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CustomerService {
    void seedCustomers() throws IOException;

    String orderedCustomers();
}

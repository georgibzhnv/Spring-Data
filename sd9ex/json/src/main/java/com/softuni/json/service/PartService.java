package com.softuni.json.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PartService {
    void seedParts() throws Exception;
}

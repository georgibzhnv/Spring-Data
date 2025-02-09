package com.softuni.realdeal.web.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@ControllerAdvice
public class ErrorHandlerControllerAdvice {
    @ExceptionHandler
    public ModelAndView getEntityNotFoundException(EntityNotFoundException ex){
        return  new ModelAndView("error","errors", List.of(ex.getMessage()));
    }
}

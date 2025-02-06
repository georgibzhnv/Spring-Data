package com.softuni.realdeal.utils;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidatorUtil {

    <E> boolean isValid(E entity);

}
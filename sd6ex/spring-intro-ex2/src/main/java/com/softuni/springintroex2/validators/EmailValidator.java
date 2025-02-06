package com.softuni.springintroex2.validators;

import com.softuni.springintroex2.annotations.Email;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email,String> {
    private static final String USER_REGEX = "^[a-zA-Z0-9]+([._-]?[a-zA-Z0-9]+)*$";
    private static final String HOST_REGEX = "^[a-zA-Z]+([a-zA-Z-]*[a-zA-Z])?(\\.[a-zA-Z]+([a-zA-Z-]*[a-zA-Z])?)+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^" + USER_REGEX + "@" + HOST_REGEX + "$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }
}

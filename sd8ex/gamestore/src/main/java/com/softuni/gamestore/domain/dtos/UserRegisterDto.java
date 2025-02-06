package com.softuni.gamestore.domain.dtos;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserRegisterDto {

    @Pattern(regexp = "\\w+@+.+", message = "Email incorrect.")
    private String email;

    @Length(min=6,message = "Password length should be at least 6 symbols.")
    @Pattern(regexp = "[A-Z]+[a-z]+[0-9]+",message = "Password should have at least 1 uppercase/lower/digit.")
    private String password;
    private String confirmedPassword;
    private String fullName;

    public UserRegisterDto(String email, String password, String confirmedPassword, String fullName) {
        this.email = email;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
        this.fullName = fullName;
    }

    public UserRegisterDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

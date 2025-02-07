package com.softuni.realdeal.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name="roles")
public class Role extends BaseEntity implements GrantedAuthority {

    private String authority;

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}

package entities.demo_hcf;

import entities.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public abstract class Person extends BaseEntity {
    private String firstName;
    private String lastName;
    private int phoneNumber;

    @Column(name = "first_name",nullable = false, unique = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name",nullable = false, unique = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "phone_number", unique = true)
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Person() {
    }


}

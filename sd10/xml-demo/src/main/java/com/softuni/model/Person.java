package com.softuni.model;

import jakarta.xml.bind.annotation.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Person {

    @XmlAttribute(required = true)
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Address address;
    @XmlElementWrapper(name = "phoneNumbers")
    @XmlElement(name = "phone")
    private Set<PhoneNumber>phoneNumbers=new HashSet<>();
}

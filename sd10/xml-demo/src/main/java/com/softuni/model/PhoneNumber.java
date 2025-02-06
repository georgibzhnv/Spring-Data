package com.softuni.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private static long nextId=0;
    @XmlAttribute(required = true)
    private Long id=++nextId;
    @NonNull
    private String number;
}

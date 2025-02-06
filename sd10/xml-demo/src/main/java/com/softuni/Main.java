package com.softuni;

import com.softuni.model.Address;
import com.softuni.model.Person;
import com.softuni.model.Persons;
import com.softuni.model.PhoneNumber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Address> addresses =List.of(
                new Address(1L,"Bulgaria","Sofia","bul. Tzar Osvoboditel 50"),
                new Address(2L,"Bulgaria","Plovdiv","bul. Gladston 17")
        );
        List<Person> persons = List.of(
                new Person(1L,"Petur","Petrov",addresses.get(0),
                Set.of(new PhoneNumber("+35 885 123456"),new PhoneNumber("+359 2 9731425"))),
        new Person(2L,"Georgi","Hristov",addresses.get(1),
                Set.of(new PhoneNumber("+35 887 456677"),new PhoneNumber("+359 32 6542370"))),
        new Person(3L,"Stamat","Petrov",addresses.get(0),
                Set.of(new PhoneNumber("+35 889 567895"),new PhoneNumber("+359 2 9731425")))
        );

        //Create JAXBContext
        try {
            JAXBContext ctx = JAXBContext.newInstance(Person.class, Persons.class);
            //Create marshaller
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            //Marshal POJO to XML
            marshaller.marshal(persons.get(0),new File("person.xml"));
//            marshaller.marshal(persons.get(0),System.out);
            //Marshal multiple persons to persons.xml
            marshaller.marshal(new Persons(persons),new File("persons.xml"));
            StringWriter out = new StringWriter();
            marshaller.marshal(new Persons(persons),out);
//            System.out.printf("StringWriter:%s%n",out.toString());

            //Unmarshal multiple Persons from XML to Java
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
           Persons unmarshalled = (Persons)unmarshaller.unmarshal(new File("persons.xml"));
            unmarshalled.getPersons().forEach(p-> System.out.printf("| %5d | %-15.15s | %-15.15s | | %-80.80s | | %-40.40s |%n",p.getId(),p.getFirstName(),p.getLastName(),p.getAddress(),
                    p.getPhoneNumbers()
                            .stream()
                            .map(PhoneNumber::getNumber)
                            .collect(Collectors.joining(", "))));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }


    }
}
package com.softuni.realdeal.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.io.StringWriter;

public class XmlParserImpl implements XmlParser{

    @Override
    @SuppressWarnings("unchecked")
    public <O> O parseXml(Class<O> object, String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (O) unmarshaller.unmarshal(new File(filePath));
    }

    @Override
    public <O> void exportXml(O object, Class<O> objectClass, String filePath) throws JAXBException {
        JAXBContext context= JAXBContext.newInstance(objectClass);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(object,new File(filePath));
    }

    @Override
    public <O> String exportXml(O object, Class<O> objectClass) throws JAXBException {
        JAXBContext context= JAXBContext.newInstance(objectClass);
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
        marshaller.marshal(object,stringWriter);
        return stringWriter.toString();
    }

}

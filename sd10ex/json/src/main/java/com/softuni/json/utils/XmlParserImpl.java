package com.softuni.json.utils;

import com.softuni.json.config.LocalDateAdapter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

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

}

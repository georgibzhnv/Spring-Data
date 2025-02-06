package com.softuni.realdeal.utils;

import jakarta.xml.bind.JAXBException;

public interface XmlParser {

    <O> O parseXml(Class<O>object,String filePath) throws JAXBException;

    <O> void exportXml (O object,Class<O>objectClass,String filePath) throws JAXBException;
}

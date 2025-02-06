package com.softuni.json.service.impl;

import com.google.gson.Gson;
import com.softuni.json.domain.dto.export.last.SaleExportDto;
import com.softuni.json.domain.dto.export.last.SaleExportRootDto;
import com.softuni.json.domain.entity.Car;
import com.softuni.json.domain.entity.Customer;
import com.softuni.json.domain.entity.Sale;
import com.softuni.json.domain.repository.CarRepository;
import com.softuni.json.domain.repository.CustomerRepository;
import com.softuni.json.domain.repository.SaleRepository;
import com.softuni.json.service.SaleService;
import com.softuni.json.utils.XmlParser;
import jakarta.xml.bind.JAXBException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final static String SALES_DISCOUNT_PATH="C:\\Users\\Георги\\Desktop\\spring_data\\sd10ex\\json\\src\\main\\resources\\xmls\\exported\\sale-discounts.xml";
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSales() throws Exception {
        Sale sale = new Sale();
        sale.setCustomer(getRandomCustomer());
        sale.setCar(getRandomCar());
        sale.setDiscount(5);

        Sale sale1 = new Sale();
        sale1.setCustomer(getRandomCustomer());
        sale1.setCar(getRandomCar());
        sale1.setDiscount(10);

        Sale sale2 = new Sale();
        sale2.setCustomer(getRandomCustomer());
        sale2.setCar(getRandomCar());
        sale2.setDiscount(30);

        this.saleRepository.saveAndFlush(sale);
        this.saleRepository.saveAndFlush(sale1);
        this.saleRepository.saveAndFlush(sale2);
    }

    @Override
    public void saleDiscounts() throws JAXBException {
        SaleExportRootDto saleExportRootDto = new SaleExportRootDto();
        List<SaleExportDto> saleExportDtos = new ArrayList<>();
        for (Sale sale : this.saleRepository.findAll()) {
            SaleExportDto saleExportDto = this.modelMapper.map(sale,SaleExportDto.class);
            saleExportDto.setDiscount(saleExportDto.getDiscount()/100);
            double totalPrice = sale.getCar().getParts()
                    .stream()
                    .mapToDouble(p->
                            Double.parseDouble(p.getPrice().toString()))
                    .sum();
            saleExportDto.setPrice(BigDecimal.valueOf(totalPrice));

            double priceWithDiscount = totalPrice-(totalPrice*sale.getDiscount()*1.0/100);
            saleExportDto.setPriceWithDiscount(BigDecimal.valueOf(priceWithDiscount));

            saleExportDtos.add(saleExportDto);
        }

        saleExportRootDto.setSales(saleExportDtos);
        this.xmlParser.exportXml(saleExportRootDto,SaleExportRootDto.class,SALES_DISCOUNT_PATH);
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.customerRepository.count())+1;
        return this.customerRepository.findById(id).get();
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long)random.nextInt((int)this.carRepository.count())+1;
        return this.carRepository.findById(id).get();
    }
}

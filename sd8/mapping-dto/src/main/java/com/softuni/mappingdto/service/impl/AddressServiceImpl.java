package com.softuni.mappingdto.service.impl;

import com.softuni.mappingdto.dao.AddressRepository;
import com.softuni.mappingdto.entity.Address;
import com.softuni.mappingdto.exception.NonexistingEntityException;
import com.softuni.mappingdto.service.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional()
public class AddressServiceImpl implements AddressService {
    private AddressRepository addressRepo;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepo) {
        this.addressRepo = addressRepo;
    }

    @Override
    public List<Address> getAllAddresss() {
        return addressRepo.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepo.findById(id).orElseThrow(
                ()-> new NonexistingEntityException(String.format("Address with ID= %d does not exist.")
                ));
    }

    @Override
    @Transactional
    public Address addAddress(Address address) {
        address.setId(null);
        return addressRepo.save(address);
    }

    @Override
    @Transactional
    public Address updateAddress(Address address) {
        getAddressById(address.getId());
        return addressRepo.save(address);
    }

    @Override
    @Transactional
    public Address deleteAddress(Long id) {
        Address removed = getAddressById(id);
        addressRepo.deleteById(id);
        return removed;
    }

    @Override
    public long getAddressCount() {
        return addressRepo.count();
    }
}

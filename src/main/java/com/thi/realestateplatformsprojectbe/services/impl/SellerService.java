package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.CustomerUpdateDTO;
import com.thi.realestateplatformsprojectbe.models.Seller;
import com.thi.realestateplatformsprojectbe.repositories.ISellerRepository;
import com.thi.realestateplatformsprojectbe.services.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService implements ISellerService {

    @Autowired
    private ISellerRepository sellerRepository;

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }

    @Override
    public Seller addSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public Seller findByAccountId(Long id) {
        return  sellerRepository.findSellerByAccount_Id(id);
    }

    @Override
    public List<Seller> searchSellers(String code, String name, String email, String phoneNumber) {
        return sellerRepository.searchSellers(code, name, email, phoneNumber);
    }

    @Override
    public void update(Long id, CustomerUpdateDTO customer) {
        Seller seller = sellerRepository.findSellerByAccount_Id(id);
        if(customer.getImageUrl() != null) {
            seller.setImageUrl(customer.getImageUrl());
        } else {
            seller.setName(customer.getName());
            seller.setDob(customer.getDob());
            seller.setAddress(customer.getAddress());
            seller.setPhoneNumber(customer.getPhoneNumber());
            seller.setIdCard(customer.getIdCard());
            seller.setGender(customer.getGender());
        }
        sellerRepository.save(seller);
    }

}
package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Buyer;
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
}
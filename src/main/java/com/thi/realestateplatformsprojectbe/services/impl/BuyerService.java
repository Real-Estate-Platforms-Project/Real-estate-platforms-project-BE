package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.repositories.IBuyerRepository;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class BuyerService implements IBuyerService {

    @Autowired
    private IBuyerRepository buyerRepository;

    @Override
    public List<Buyer> getAllBuyers() {
        return buyerRepository.findAll();
    }

    @Override
    public Buyer getBuyerById(Long id) {
        return buyerRepository.findById(id).orElse(null);
    }

    @Override
    public Buyer addBuyer(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public String generateBuyerCode() {
        Random random = new Random();
        int number = random.nextInt(900000) + 100000;
        return "REP-" + number;
    }

    @Override
    public void save(Buyer user) {
        buyerRepository.save(user);
    }

    @Override
    public Buyer findByAccountId(Long id) {
        return buyerRepository.findBuyerByAccount_Id(id);
    }

    @Override
    public List<Buyer> searchBuyers(String code, String name, String email, String phoneNumber) {
        return buyerRepository.searchBuyers(code, name, email, phoneNumber);
    }
}
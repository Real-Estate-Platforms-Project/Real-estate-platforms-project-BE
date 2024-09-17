package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.CustomerUpdateDTO;
import com.thi.realestateplatformsprojectbe.models.Account;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.repositories.IAccountRepository;
import com.thi.realestateplatformsprojectbe.repositories.IBuyerRepository;
import com.thi.realestateplatformsprojectbe.repositories.IRoleRepository;
import com.thi.realestateplatformsprojectbe.services.IBuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
    @Override
    public Buyer getBuyerByAccountId(Long id) {
        return buyerRepository.findBuyerByAccount_Id(id);
    }

    @Override
    public void createBuyerRegister(Account account) {
        Buyer user = new Buyer();
        user.setAccount(account);
        user.setName(account.getName());
        user.setEmail(account.getEmail());
        user.setCode(generateBuyerCode());
        user.setAddress("");
        user.setEnable(true);
        user.setGender("");
        user.setDob(LocalDate.of(2000, 1, 1));
        user.setIdCard("");
        user.setPhoneNumber("");
        user.setImageUrl("");
        save(user);
    }

    @Override
    public void update(Long id, CustomerUpdateDTO customer) {
        Buyer buyer = buyerRepository.findBuyerByAccount_Id(id);
        if(customer.getImageUrl() != null) {
            buyer.setImageUrl(customer.getImageUrl());
        } else {
            buyer.setName(customer.getName());
            buyer.setDob(customer.getDob());
            buyer.setAddress(customer.getAddress());
            buyer.setPhoneNumber(customer.getPhoneNumber());
            buyer.setIdCard(customer.getIdCard());
            buyer.setGender(customer.getGender());
        }
        buyerRepository.save(buyer);
    }
}
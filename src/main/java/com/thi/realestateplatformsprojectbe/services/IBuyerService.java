package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.IUser;

import java.util.List;

public interface IBuyerService {
    List<Buyer> getAllBuyers();
    Buyer getBuyerById(Long id);
    Buyer addBuyer(Buyer buyer);

    String generateBuyerCode();

    void save(Buyer user);

    Buyer findByAccountId(Long id);

    Buyer getBuyerByAccountId(Long id);
}
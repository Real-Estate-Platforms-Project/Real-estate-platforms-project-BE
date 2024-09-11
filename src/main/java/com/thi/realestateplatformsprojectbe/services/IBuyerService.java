package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import java.util.List;

public interface IBuyerService {
    List<Buyer> getAllBuyers();
    Buyer getBuyerByAccountId(Long id);
    Buyer addBuyer(Buyer buyer);

    Buyer getBuyerById(Long buyerId);
}
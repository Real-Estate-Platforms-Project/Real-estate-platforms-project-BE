package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Seller;
import java.util.List;

public interface ISellerService {
    List<Seller> getAllSellers();
    Seller getSellerById(Long id);
    Seller addSeller(Seller seller);
    Seller findByAccountId(Long id);
    List<Seller> searchSellers(String code, String name, String email, String phoneNumber);

}
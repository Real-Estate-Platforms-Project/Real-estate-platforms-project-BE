package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Seller;
import java.util.List;

public interface ISellerService {
    List<Seller> getAllSellers();
    Seller getSellerById(Long id);
    Seller addSeller(Seller seller);
}
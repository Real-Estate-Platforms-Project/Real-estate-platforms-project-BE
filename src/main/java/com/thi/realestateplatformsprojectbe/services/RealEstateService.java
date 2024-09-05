package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.repositories.IRealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService implements IRealEstateService{

    @Autowired
    private IRealEstateRepository realEstateRepository;

    @Override
    public RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO) {
        return null;
    }
}

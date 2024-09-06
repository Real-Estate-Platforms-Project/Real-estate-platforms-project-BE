package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.repositories.IRealEstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RealEstateService implements IRealEstateService{

    @Autowired
    private IRealEstateRepository realEstateRepository;

    @Override
    public RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO) {
        return null;
    }

    @Override
    public Page<RealEstate> searchRealEstates(Double minPrice, Double maxPrice, String region, String type, Integer minArea, Integer maxArea, Pageable pageable) {
        return realEstateRepository.searchRealEstates(minPrice,maxPrice,region,type,minArea,maxArea,pageable);
    }
}

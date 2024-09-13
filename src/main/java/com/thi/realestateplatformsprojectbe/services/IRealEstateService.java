package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRealEstateService {
    RealEstate addRealEstatePost(RealEstateWithDetailDTO realEstatePostDTO);
    Page<RealEstate> searchRealEstates(String address,Double minPrice, Double maxPrice, List<String> location, List<String> demandType, Integer minArea, Integer maxArea, Pageable pageable);

    List<RealEstate> getAll();
    RealEstate findById(Long id);
}
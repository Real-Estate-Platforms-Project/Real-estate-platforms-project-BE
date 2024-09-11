package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRealEstateService {
    RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO);
    Page<RealEstate> searchRealEstates(String address,Double minPrice, Double maxPrice, String location, String type, Integer minArea, Integer maxArea, Pageable pageable);

    List<RealEstate> getAll();
}

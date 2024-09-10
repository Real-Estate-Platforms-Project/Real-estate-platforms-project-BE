package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRealEstateService {
    RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO);
    Page<RealEstate> searchRealEstates(Double minPrice, Double maxPrice, String location, String type, Integer minArea, Integer maxArea, Pageable pageable);

}

package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;

public interface IRealEstateService {
    RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO);
}

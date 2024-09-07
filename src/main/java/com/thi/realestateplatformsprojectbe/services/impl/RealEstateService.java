package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.RealEstateDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.repositories.IRealEstateRepository;
import com.thi.realestateplatformsprojectbe.repositories.ISellerRepository;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class RealEstateService implements IRealEstateService {

    @Autowired
    private IRealEstateRepository realEstateRepository;

    @Autowired
    private ISellerRepository sellerRepository;

    @Override
    public RealEstate addRealEstatePost(RealEstateDTO realEstatePostDTO) {
        RealEstate realEstate = new RealEstate();
        realEstate.setDemandType(realEstatePostDTO.getDemandType());
        realEstate.setType(realEstatePostDTO.getType());
        realEstate.setAddress(realEstatePostDTO.getAddress());
        realEstate.setLocation(realEstatePostDTO.getLocation());
        realEstate.setDirection(realEstatePostDTO.getDirection());
        realEstate.setArea(realEstatePostDTO.getArea());
        realEstate.setPrice(realEstatePostDTO.getPrice());
        realEstate.setStatus(realEstatePostDTO.getStatus());
        realEstate.setNote(realEstatePostDTO.getNote());
        realEstate.setSeller(sellerRepository.findById(realEstatePostDTO.getSellerId()).orElse(null));
        return realEstateRepository.save(realEstate);
    }


    @Override
    public Page<RealEstate> searchRealEstates(Double minPrice, Double maxPrice, String location, String type, Integer minArea, Integer maxArea, Pageable pageable) {
        return realEstateRepository.searchRealEstates(minPrice,maxPrice,location,type,minArea,maxArea,pageable);
    }

}

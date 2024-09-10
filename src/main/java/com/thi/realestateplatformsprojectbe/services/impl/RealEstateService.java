package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.models.RealEstateDetail;
import com.thi.realestateplatformsprojectbe.repositories.*;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RealEstateService implements IRealEstateService {


    private final IRealEstateRepository realEstateRepository;
    private final ISellerRepository sellerRepository;
    private final IProvinceRepository provinceRepository;
    private final IDistrictRepository districtRepository;
    private final IWardRepository wardRepository;
    private final IRealEstateDetailRepository realEstateDetailRepository;

    @Override
    public RealEstate addRealEstatePost(RealEstateWithDetailDTO realEstatePostDTO) {
        // Sử dụng Builder để tạo đối tượng RealEstate
        RealEstate realEstate = RealEstate.builder()
                .seller(sellerRepository.findById(realEstatePostDTO.getSellerId()).orElse(null))
                .demandType(realEstatePostDTO.getDemandType())
                .type(realEstatePostDTO.getType())
                .address(realEstatePostDTO.getAddress())
                .location(realEstatePostDTO.getLocation())
                .direction(realEstatePostDTO.getDirection())
                .area(realEstatePostDTO.getArea())
                .price(realEstatePostDTO.getPrice())
                .status(realEstatePostDTO.getStatus())
                .note(realEstatePostDTO.getNote())
                .province(provinceRepository.findProvinceByCode(realEstatePostDTO.getProvinceCode()))
                .district(districtRepository.findDistrictByCode(realEstatePostDTO.getDistrictCode()))
                .ward(wardRepository.findWardByCode(realEstatePostDTO.getWardCode()))
                .build();
        RealEstate savedRealEstate = realEstateRepository.save(realEstate);
        // Sử dụng Builder để tạo đối tượng RealEstateDetail
        RealEstateDetail realEstateDetail = RealEstateDetail.builder()
                .bedroom(realEstatePostDTO.getNumberOfBedrooms())
                .floor(realEstatePostDTO.getNumberOfFloors())
                .toilet(realEstatePostDTO.getNumberOfToilet())
                .realEstate(savedRealEstate)
                .build();
        realEstateDetailRepository.save(realEstateDetail);

        return savedRealEstate;
    }


    @Override
    public Page<RealEstate> searchRealEstates(Double minPrice, Double maxPrice, String location, String type, Integer minArea, Integer maxArea, Pageable pageable) {
        return realEstateRepository.searchRealEstates(minPrice,maxPrice,location,type,minArea,maxArea,pageable);
    }

}

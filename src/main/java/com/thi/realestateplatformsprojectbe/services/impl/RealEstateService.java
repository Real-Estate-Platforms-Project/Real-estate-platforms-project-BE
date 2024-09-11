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

import java.util.Random;

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
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        String generatedCode = "MBDS-" + randomNumber;
        // Create the RealEstate object using the Builder pattern
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
                .code(generatedCode)
                .build();
        RealEstate savedRealEstate = realEstateRepository.save(realEstate);
        // Conditionally create and save RealEstateDetail if type is "Nhà ở"
        if ("Nhà ở".equals(realEstatePostDTO.getType())) {
            RealEstateDetail realEstateDetail = RealEstateDetail.builder()
                    .bedroom(realEstatePostDTO.getBedroom())
                    .floor(realEstatePostDTO.getFloor())
                    .toilet(realEstatePostDTO.getToilet())
                    .realEstate(savedRealEstate)
                    .build();
            realEstateDetailRepository.save(realEstateDetail);
        }
        return savedRealEstate;
    }



    @Override
    public Page<RealEstate> searchRealEstates(Double minPrice, Double maxPrice, String location, String type, Integer minArea, Integer maxArea, Pageable pageable) {
        return realEstateRepository.searchRealEstates(minPrice,maxPrice,location,type,minArea,maxArea,pageable);
    }

}

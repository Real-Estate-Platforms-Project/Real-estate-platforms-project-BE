package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
import com.thi.realestateplatformsprojectbe.models.Image;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.models.RealEstateDetail;
import com.thi.realestateplatformsprojectbe.repositories.*;
import com.thi.realestateplatformsprojectbe.services.IRealEstateService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RealEstateService implements IRealEstateService {


    private final IRealEstateRepository realEstateRepository;
    private final ISellerRepository sellerRepository;
    private final IProvinceRepository provinceRepository;
    private final IDistrictRepository districtRepository;
    private final IWardRepository wardRepository;
    private final IRealEstateDetailRepository realEstateDetailRepository;
    private final IImageRepository imageRepository;

    @Override
    public RealEstate addRealEstatePost(RealEstateWithDetailDTO realEstatePostDTO) {
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000);
        String generatedCode = "MBDS-" + randomNumber;
        Set<Image> images = new HashSet<>();
        if (realEstatePostDTO.getImageUrls() != null) {
            for (String imageUrl : realEstatePostDTO.getImageUrls()) {
                Image image = Image.builder()
                        .name(imageUrl)
                        .build();
                image = imageRepository.save(image);
                images.add(image);
            }
        }
        RealEstate realEstate = RealEstate.builder()
                .seller(sellerRepository.findById(realEstatePostDTO.getSellerId()).orElse(null))
                .title(realEstatePostDTO.getTitle())
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
                .images(images) // Associate multiple images
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
    public List<RealEstate> getAll() {
        return realEstateRepository.findAll();
    }


    @Override
    public Page<RealEstate> searchRealEstates(String address,Double minPrice, Double maxPrice, List<String> location, List<String> demandType, Integer minArea, Integer maxArea, Pageable pageable) {
        return realEstateRepository.searchRealEstates(address,minPrice,maxPrice,location,demandType,minArea,maxArea,pageable);
    }

    @Override
    public RealEstate findById(Long id) {
        return realEstateRepository.findById(id).orElse(null);
    }

}

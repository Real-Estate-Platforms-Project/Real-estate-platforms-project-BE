package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;

import java.util.List;

public interface IDemandService {
    List<Demand> findAllVerifiedDemand(boolean isVerify);

    List<Demand> findAll();

    void delete(Demand demand);

    List<Demand> findInvalidatedDemand();

    boolean verifyDemand(Long id);

    Demand save(DemandDTO demandDTO);

    Demand findById(Long id);

    List<Demand> searchDemand(String notes, String region, String type, String realEstateType, Integer minArea, Integer maxArea);
}

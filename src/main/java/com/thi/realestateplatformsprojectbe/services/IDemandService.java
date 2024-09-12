package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;

import java.util.List;

public interface IDemandService {
    List<Demand> findAllVerifiedDemand();

    List<Demand> findAll();

    void delete(Demand demand);

    List<Demand> findInvalidatedDemand();

    void verifyDemand(Long id);

    Demand save(DemandDTO demandDTO, Buyer buyer);

    Demand findById(Long id);

    List<Demand> searchDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea);

    List<Demand> searchVerifiedDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, boolean isVerify);
}

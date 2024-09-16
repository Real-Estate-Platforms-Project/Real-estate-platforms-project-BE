package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDemandService {

    Page<Demand> findAll(Integer page);

    void delete(Demand demand);

    void verifyDemand(Long id);

    Demand save(DemandDTO demandDTO, Buyer buyer);

    Demand findById(Long id);

    Page<Demand> searchDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, Pageable pageable);

    Page<Demand> searchVerifiedDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, boolean isVerify, Pageable pageable);

    Page<Demand> searchAccountDemand(Long buyerId, String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, Pageable pageable);

    void edit(Demand demand);
}

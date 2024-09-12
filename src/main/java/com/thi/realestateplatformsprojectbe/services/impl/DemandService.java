package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.repositories.IDemandRepository;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DemandService implements IDemandService {
    @Autowired
    private IDemandRepository demandRepository;

    @Autowired
    private BuyerService buyerService;


    @Override
    public List<Demand> findAllVerifiedDemand() {
        return demandRepository.findAllByIsDeletedAndIsVerifyOrderByCreatedAtDesc(false,true);
    }

    @Override
    public  List<Demand> findAll(){
        return demandRepository.findAllByIsDeletedOrderByIsVerifyAscCreatedAtDesc(false);
    }

    @Override
    public void delete(Demand demand) {
        demand.setIsDeleted(true);
        demandRepository.save(demand);
    }

    @Override
    public List<Demand> findInvalidatedDemand() {
        return demandRepository.findInvalidatedDemand();
    }

    @Override
    public void verifyDemand(Long id) {
        if (demandRepository.findById(id).isPresent()) {
            Demand demand = demandRepository.findById(id).get();
            if (!demand.getIsVerify()) {
                demand.setIsVerify(true);
                demandRepository.save(demand);
            }
        }
    }

    @Override
    public Demand save(DemandDTO demandDTO, Buyer buyer) {
//        buyerService.getBuyerById(demandDTO.getBuyerId());
        Demand demand = Demand.builder()
//                .buyer(buyerService.getBuyerById(demandDTO.getBuyerId()))
                .buyer(buyer)
                .region(demandDTO.getRegion())
                .type(demandDTO.getType())
                .realEstateType(demandDTO.getRealEstateType())
                .title(demandDTO.getTitle())
                .notes(demandDTO.getNotes())
                .minArea(demandDTO.getMinArea())
                .maxArea(demandDTO.getMaxArea())
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .isVerify(false)
                .build();
        demandRepository.save(demand);
        return demand;
    }

    @Override
    public Demand findById(Long id) {
        return demandRepository.findById(id).orElse(null);
    }

    @Override
    public List<Demand> searchVerifiedDemand(String notes, String region, String type, String realEstateType, Integer minArea, Integer maxArea, boolean isVerify) {
        return demandRepository.searchVerifiedDemands(notes,region,type,realEstateType,minArea,maxArea,isVerify);
    }

    @Override
    public List<Demand> searchDemand(String notes, String region, String type, String realEstateType, Integer minArea, Integer maxArea) {
        return demandRepository.searchDemands(notes,region,type,realEstateType,minArea,maxArea);
    }

//    @Override
//    public List<Demand> searchDemand(String notes, String region, String type, String realEstateType, Integer minArea, Integer maxArea,Boolean isVerify) {
//        return demandRepository.searchDemands(notes,region,type,realEstateType,minArea,maxArea,isVerify);
//    }
}

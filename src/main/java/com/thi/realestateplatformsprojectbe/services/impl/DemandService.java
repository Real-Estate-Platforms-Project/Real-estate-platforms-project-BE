package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.configs.service.JwtService;
import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.repositories.IDemandRepository;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandService implements IDemandService {
    @Autowired
    private IDemandRepository demandRepository;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private JwtService jwtService;

    @Override
    public List<Demand> findAllVerifiedDemand(boolean isVerify) {
        if(!isVerify) {
            return demandRepository.findAllByIsDeletedAndIsVerify(false,false);
        }
        return demandRepository.findAllByIsDeletedAndIsVerify(false,true);
    }

    @Override
    public  List<Demand> findAll(){
        return demandRepository.findAllByIsDeleted(false);
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
    public boolean verifyDemand(Long id) {
        if (demandRepository.findById(id).isPresent()) {
            Demand demand = demandRepository.findById(id).get();
            if (!demand.getIsVerify()) {
                demand.setIsVerify(true);
                demandRepository.save(demand);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Demand save(DemandDTO demandDTO) {
        buyerService.getBuyerById(demandDTO.getBuyerId());
        Demand demand = Demand.builder()
                .buyer(buyerService.getBuyerById(demandDTO.getBuyerId()))
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
}

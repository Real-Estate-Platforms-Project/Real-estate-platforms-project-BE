package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.dto.DemandDTO;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.repositories.IDemandRepository;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DemandService implements IDemandService {
    @Autowired
    private IDemandRepository demandRepository;


    @Override
    public Page<Demand> findAll(Integer page) {
        Pageable pages = PageRequest.of(page, 6);
        return demandRepository.findAllByIsDeletedOrderByIsVerifyAscCreatedAtDesc(false, pages);
    }

    @Override
    public void delete(Demand demand) {
        demand.setIsDeleted(true);
        demandRepository.save(demand);
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

        Demand demand = Demand.builder()
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
        if (demandDTO.getId() == null) {
            demandRepository.save(demand);
        } else {
            demand.setId(demandDTO.getId());
        }
        return demand;
    }

    @Override
    public Demand findById(Long id) {
        return demandRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Demand> searchVerifiedDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, boolean isVerify, Pageable pageable) {
        return demandRepository.searchVerifiedDemands(notes, region, type, realEstateType, minArea, maxArea, isVerify, pageable);
    }

    @Override
    public Page<Demand> searchAccountDemand(Long buyer, String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, Pageable pageable) {
        return demandRepository.searchDemandBuyer(buyer, notes, region, type, realEstateType, minArea, maxArea, pageable);
    }

    @Override
    public void edit(Demand demand) {
        demandRepository.save(demand);
    }

    @Override
    public Page<Demand> searchDemand(String notes, List<String> region, String type, List<String> realEstateType, Integer minArea, Integer maxArea, Pageable pageable) {
        return demandRepository.searchDemands(notes, region, type, realEstateType, minArea, maxArea, pageable);
    }

}
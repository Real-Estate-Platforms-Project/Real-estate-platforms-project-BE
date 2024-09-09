package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.repositories.IDemandRepository;
import com.thi.realestateplatformsprojectbe.services.IDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandService implements IDemandService {
    @Autowired
    private IDemandRepository demandRepository;
    @Override
    public List<Demand> findAll() {
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
    public void save(Demand demand) {
        demandRepository.save(demand);
    }

    @Override
    public Demand findById(Long id) {
        return demandRepository.findById(id).orElse(null);
    }
}

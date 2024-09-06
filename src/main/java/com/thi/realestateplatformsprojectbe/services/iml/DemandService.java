package com.thi.realestateplatformsprojectbe.services.iml;

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
        return demandRepository.findAll();
    }

    @Override
    public void delete(Demand demand) {
        demandRepository.delete(demand);
    }

    @Override
    public List<Demand> findInvalidatedDemand() {
        return demandRepository.findInvalidatedDemand();
    }

    @Override
    public boolean verifyDemand(Long id) {
        if (demandRepository.findById(id).isPresent()) {
            if (!demandRepository.findById(id).get().getIsVerify()) {
                demandRepository.findById(id).get().setIsVerify(true);
                return true;
            }
            return false;
        }
        return false;
    }
}

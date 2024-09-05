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
}

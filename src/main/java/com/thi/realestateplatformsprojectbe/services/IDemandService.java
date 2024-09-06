package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Demand;

import java.util.List;

public interface IDemandService {
    List<Demand> findAll();

    void delete(Demand demand);

    List<Demand> findInvalidatedDemand();

    boolean verifyDemand(Long id);
}

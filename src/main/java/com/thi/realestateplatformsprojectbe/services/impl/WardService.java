package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Ward;
import com.thi.realestateplatformsprojectbe.repositories.IWardRepository;
import com.thi.realestateplatformsprojectbe.services.IWardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WardService implements IWardService {

    @Autowired
    private IWardRepository wardRepository;


    @Override
    public List<Ward> findAllByDistrictCode(String code) {
        return wardRepository.findAllByDistrictCode(code);
    }
}

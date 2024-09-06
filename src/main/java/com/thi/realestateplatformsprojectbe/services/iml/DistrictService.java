package com.thi.realestateplatformsprojectbe.services.iml;

import com.thi.realestateplatformsprojectbe.models.District;
import com.thi.realestateplatformsprojectbe.repositories.IDistrictRepository;
import com.thi.realestateplatformsprojectbe.services.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService implements IDistrictService {

    @Autowired
    private IDistrictRepository districtRepository;

    @Override
    public List<District> findAllByProvinceCode(String code) {
        return districtRepository.findAllByProvinceCode(code);
    }
}

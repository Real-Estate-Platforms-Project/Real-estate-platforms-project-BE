package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.District;

import java.util.List;

public interface IDistrictService {
    List<District> findAllByProvinceCode(String code);
}

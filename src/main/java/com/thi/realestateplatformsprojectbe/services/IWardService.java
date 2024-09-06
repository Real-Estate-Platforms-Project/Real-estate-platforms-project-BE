package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.Ward;

import java.util.List;

public interface IWardService {

    List<Ward> findAllByDistrictCode(String code);
}

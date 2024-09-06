package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IDistrictRepository extends JpaRepository<District, String> {

    List<District> findAllByProvinceCode(String code);
}

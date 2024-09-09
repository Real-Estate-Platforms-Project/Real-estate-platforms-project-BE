package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProvinceRepository extends JpaRepository<Province,String> {
    Province findProvinceByCode(String provinceCode);
}

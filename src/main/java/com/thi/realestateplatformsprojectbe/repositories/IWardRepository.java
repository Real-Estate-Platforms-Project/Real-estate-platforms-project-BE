package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWardRepository extends JpaRepository<Ward,String> {
    List<Ward> findAllByDistrictCode(String code);
}

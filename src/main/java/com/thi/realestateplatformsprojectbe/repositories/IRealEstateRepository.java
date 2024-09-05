package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRealEstateRepository extends JpaRepository<RealEstate, Long> {
}

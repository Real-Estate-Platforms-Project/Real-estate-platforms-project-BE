package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDemandRepository extends JpaRepository<Demand, Long> {
}

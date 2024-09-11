package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Demand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDemandRepository extends JpaRepository<Demand, Long> {
    @Query(nativeQuery = true, value = "select * from demands as s where s.isVerify = 0")
    List<Demand> findInvalidatedDemand();

    List<Demand> findAllByIsDeleted(Boolean isDeleted);
    List<Demand> findAllByIsDeletedAndIsVerify(Boolean isDeleted, Boolean isVerify);
}

package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Demand;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDemandRepository extends JpaRepository<Demand, Long> {
    @Query(nativeQuery = true, value = "select * from demands as s where s.isVerify = 0")
    List<Demand> findInvalidatedDemand();

    List<Demand> findAllByIsDeleted(Boolean isDeleted);
    List<Demand> findAllByIsDeletedAndIsVerify(Boolean isDeleted, Boolean isVerify);

    @Query("SELECT r FROM Demand r WHERE "
            + "(:notes IS NULL OR r.notes LIKE %:notes%) AND "
            + "(:region IS NULL OR r.region = :region) AND "
            + "(:type IS NULL OR r.type = :type) AND "
            + "(:realEstateType IS NULL OR r.realEstateType = :realEstateType) AND "
            + "(:minArea IS NULL OR r.minArea >= :minArea) AND "
            + "(:maxArea IS NULL OR r.maxArea <= :maxArea)")

    List<Demand> searchDemands(
            @Param("notes") String notes,
            @Param("region") String region,
            @Param("type") String type,
            @Param("realEstateType") String realEstateType,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea);
}

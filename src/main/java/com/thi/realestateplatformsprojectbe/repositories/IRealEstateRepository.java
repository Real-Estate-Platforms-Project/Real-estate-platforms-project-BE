package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRealEstateRepository extends JpaRepository<RealEstate, Long> {
    @Query("SELECT r FROM RealEstate r WHERE "
            + "(:address IS NULL OR r.address LIKE %:address%) AND "
            + "(:minPrice IS NULL OR r.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR r.price <= :maxPrice) AND "
            + "(:location IS NULL OR r.location IN :location) AND "
            + "(:demand_type IS NULL OR r.demandType IN :demand_type) AND "
            + "(:minArea IS NULL OR r.area >= :minArea) AND "
            + "(:maxArea IS NULL OR r.area <= :maxArea)")

    Page<RealEstate> searchRealEstates(
            @Param("address") String address,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("location") List<String> location,
            @Param("demand_type") List<String> demandType,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            Pageable pageable
    );
}
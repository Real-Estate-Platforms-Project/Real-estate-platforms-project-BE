package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.RealEstate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRealEstateRepository extends JpaRepository<RealEstate, Long> {
    @Query("SELECT r FROM RealEstate r WHERE "
            + "(:minPrice IS NULL OR r.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR r.price <= :maxPrice) AND "
            + "(:location IS NULL OR r.location LIKE %:location%) AND "
            + "(:type IS NULL OR r.type = :type) AND "
            + "(:minArea IS NULL OR r.area >= :minArea) AND "
            + "(:maxArea IS NULL OR r.area <= :maxArea)")
    Page<RealEstate> searchRealEstates(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("location") String location,
            @Param("type") String type,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            Pageable pageable
    );
}

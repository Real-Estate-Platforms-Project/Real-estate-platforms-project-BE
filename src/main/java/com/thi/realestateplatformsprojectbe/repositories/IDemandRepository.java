package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.Buyer;
import com.thi.realestateplatformsprojectbe.models.Demand;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IDemandRepository extends JpaRepository<Demand, Long> {
    @Query(nativeQuery = true, value = "select * from demands as s where s.isVerify = 0")
    List<Demand> findInvalidatedDemand();

    List<Demand> findAllByIsDeletedOrderByIsVerifyAscCreatedAtDesc(Boolean isDeleted);
    List<Demand> findAllByIsDeletedAndIsVerifyOrderByCreatedAtDesc(Boolean isDeleted, Boolean isVerify);

    @Query("SELECT r FROM Demand r WHERE "
            + "(r.isDeleted = false) AND "
            + "(:notes IS NULL OR r.notes LIKE %:notes%) AND "
            + "(:region IS NULL OR r.region IN :region) AND "
            + "(:type IS NULL OR r.type = :type) AND "
            + "(:realEstateType IS NULL OR r.realEstateType IN :realEstateType) AND "
            + "(:minArea IS NULL OR r.minArea >= :minArea) AND "
            + "(:maxArea IS NULL OR r.maxArea <= :maxArea) AND"
            + "(:isVerify IS NULL OR r.isVerify = :isVerify) order by r.createdAt desc" )
    List<Demand> searchVerifiedDemands(
            @Param("notes") String notes,
            @Param("region") List<String> region,
            @Param("type") String type,
            @Param("realEstateType") List<String> realEstateType,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea,
            @Param("isVerify") Boolean isVerify);

    @Query("SELECT r FROM Demand r WHERE "
            + "(r.isDeleted = false) AND "
            + "(:notes IS NULL OR r.notes LIKE %:notes%) AND "
            + "(:region IS NULL OR r.region IN :region) AND "
            + "(:type IS NULL OR r.type = :type) AND "
            + "(:realEstateType IS NULL OR r.realEstateType IN :realEstateType) AND "
            + "(:minArea IS NULL OR r.minArea >= :minArea) AND "
            + "(:maxArea IS NULL OR r.maxArea <= :maxArea) order by r.isVerify asc , r.createdAt desc ")
    List<Demand> searchDemands(
            @Param("notes") String notes,
            @Param("region") List<String> region,
            @Param("type") String type,
            @Param("realEstateType") List<String> realEstateType,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea);

    List<Demand> findAllByBuyer(Buyer buyer);

    @Query("SELECT r FROM Demand r WHERE "
            + "(r.isDeleted = false) AND "
            + "(:buyerId IS NULL OR r.buyer.id = :buyerId) AND "
            + "(:notes IS NULL OR r.notes LIKE %:notes%) AND "
            + "(:region IS NULL OR r.region IN :region) AND "
            + "(:type IS NULL OR r.type = :type) AND "
            + "(:realEstateType IS NULL OR r.realEstateType IN :realEstateType) AND "
            + "(:minArea IS NULL OR r.minArea >= :minArea) AND "
            + "(:maxArea IS NULL OR r.maxArea <= :maxArea) order by r.isVerify asc , r.createdAt desc ")
    List<Demand> searchDemandBuyer(
            @Param("buyerId") Long buyer_id,
            @Param("notes") String notes,
            @Param("region") List<String> region,
            @Param("type") String type,
            @Param("realEstateType") List<String> realEstateType,
            @Param("minArea") Integer minArea,
            @Param("maxArea") Integer maxArea);
}
package com.thi.realestateplatformsprojectbe.dto.statisticDTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StatisticDemandDTO {
    Long id;
    String title;
    String nameBuyer;
    String type;
    String realEstateType;
    String region;
    Integer minArea;
    Integer maxArea;
    LocalDate createdAt;
    String notes;
    Boolean isVerify;
    Boolean isDeleted;
}
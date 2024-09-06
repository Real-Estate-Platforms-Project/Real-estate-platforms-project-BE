package com.thi.realestateplatformsprojectbe.dto;

import lombok.Data;

@Data
public class RealEstateDTO {
    private Long sellerId;
    private String demandType;
    private String type;
    private String address;
    private String location;
    private String direction;
    private Double area;
    private Double price;
    private String status;
    private String note;
}

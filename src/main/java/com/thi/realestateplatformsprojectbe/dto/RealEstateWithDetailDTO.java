package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class RealEstateWithDetailDTO {

    private Long sellerId;

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    @NotBlank(message = "Loại nhu cầu không được để trống")
    private String demandType;

    @NotBlank(message = "Loại bất động sản không được để trống")
    private String type;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Vị trí không được để trống")
    private String location;

    @NotBlank(message = "Hướng không được để trống")
    private String direction;

    @NotNull(message = "Diện tích không được để trống")
    @Positive(message = "Diện tích phải là một số dương")
    private Double area;

    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải là một số dương")
    private Double price;

    @NotBlank(message = "Tình trạng không được để trống")
    private String status;

    private String note;

    @NotBlank(message = "Mã tỉnh/thành phố không được để trống")
    private String provinceCode;

    @NotBlank(message = "Mã quận/huyện không được để trống")
    private String districtCode;

    @NotBlank(message = "Mã phường/xã không được để trống")
    private String wardCode;

    private Integer floor;

    private Integer bedroom;

    private Integer toilet;

    @NotNull(message = "Ảnh không được để trống")
    private List<String> imageUrls;
}
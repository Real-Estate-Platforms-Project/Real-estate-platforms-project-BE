package com.thi.realestateplatformsprojectbe.dto;
import com.thi.realestateplatformsprojectbe.models.Buyer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
public class DemandDTO {

    @NotNull(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull(message = "Loại nhu cầu không được để trống")
    private String type;

    @NotNull(message = "Loại bất động sản không được để trống")
    private String realEstateType;

    @NotNull(message = "Khu vực không được để trống")
    private String region;

    @NotNull(message = "Diện tích không được để trống")
    @Min(value = 10,message = "Diện tích không được nhỏ hơn 10 m2")
    @Max(value = 10000,message = "Diện tích không được lớn hơn 10000 m2")
    private Integer minArea;

    @NotNull(message = "Diện tích không được để trống")
    @Min(value = 10,message = "Diện tích không được nhỏ hơn 10 m2")
    @Max(value = 10000,message = "Diện tích không được lớn hơn 10000 m2")
    private Integer maxArea;

    @NotNull(message = "Tiêu đề không được để trống")
    private String notes;
}

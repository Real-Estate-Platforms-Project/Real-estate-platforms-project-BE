package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "wards")
public class Ward {
    @Id
    private String code;
    private String name;
    @Column(name="district_code")
    private String districtCode;
}

package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "districts")
public class District {
    @Id
    private String code;
    private String name;
    @Column(name="province_code")
    private String provinceCode;

}

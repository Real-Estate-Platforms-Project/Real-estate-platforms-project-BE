package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "real_estates", schema = "real_estate_platform")
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @Size(max = 10)
    @NotNull
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Size(max = 15)
    @NotNull
    @Column(name = "region", nullable = false, length = 15)
    private String region;

    @Size(max = 15)
    @NotNull
    @Column(name = "direction", nullable = false, length = 15)
    private String direction;

    @NotNull
    @Column(name = "areas", nullable = false)
    private Integer areas;

    @NotNull
    @Column(name = "price", nullable = false)
    private Integer price;

    @Lob
    @Column(name = "notes")
    private String notes;

}
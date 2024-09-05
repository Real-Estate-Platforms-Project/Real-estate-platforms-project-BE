package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "real_estate_details", schema = "real_estate_platform")
public class RealEstateDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "front_side", nullable = false)
    private Integer frontSide;

    @NotNull
    @Column(name = "street_width", nullable = false)
    private Integer streetWidth;

    @Column(name = "distance_to_main_street")
    private Integer distanceToMainStreet;

    @NotNull
    @Column(name = "length", nullable = false)
    private Integer length;

    @NotNull
    @Column(name = "floor", nullable = false)
    private Integer floor;

    @NotNull
    @Column(name = "bedroom", nullable = false)
    private Integer bedroom;

    @NotNull
    @Column(name = "toilet", nullable = false)
    private Integer toilet;

}
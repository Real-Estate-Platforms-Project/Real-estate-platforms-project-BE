package com.thi.realestateplatformsprojectbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "real_estate_details", schema = "real_estate_platform")
public class RealEstateDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "real_estate_id", nullable = false)
    @JsonBackReference
    private RealEstate realEstate;

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
package com.thi.realestateplatformsprojectbe.models;

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

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "real_estate_id", nullable = false)
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
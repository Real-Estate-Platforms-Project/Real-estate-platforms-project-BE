package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "demands", schema = "real_estate_platform")
public class Demand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 10)
    @NotNull
    @Column(name = "code", nullable = false, length = 10)
    private String code;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Size(max = 10)
    @NotNull
    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @Size(max = 10)
    @NotNull
    @Column(name = "real_estate_type", nullable = false, length = 10)
    private String realEstateType;

    @Size(max = 15)
    @NotNull
    @Column(name = "region", nullable = false, length = 15)
    private String region;

    @NotNull
    @Column(name = "min_area", nullable = false)
    private Integer minArea;

    @NotNull
    @Column(name = "max_area", nullable = false)
    private Integer maxArea;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Lob
    @Column(name = "notes")
    private String notes;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

}
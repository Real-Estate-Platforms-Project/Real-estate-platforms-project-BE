package com.thi.realestateplatformsprojectbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "real_estates", schema = "real_estate_platform")
public class RealEstate {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 15)
    @NotNull
    @Column(name = "type", nullable = false, length = 15)
    private String type;

    @Size(max = 15)
    @NotNull
    @Column(name = "demand_type", nullable = false, length = 15)
    private String demandType;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonBackReference
    private Seller seller;

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

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

}
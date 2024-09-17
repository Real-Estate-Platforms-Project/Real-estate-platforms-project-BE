package com.thi.realestateplatformsprojectbe.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "real_estates", schema = "real_estate_platform")
@Builder
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

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
//    @JsonBackReference
    private Seller seller;

    @NotNull
    @Column(name = "address",nullable = false)
    private String address;

    @Size(max = 15)
    @NotNull
    @Column(name = "location", nullable = false, length = 15)
    private String location;

    @Size(max = 15)
    @NotNull
    @Column(name = "direction", nullable = false, length = 15)
    private String direction;

    @NotNull
    @Column(name = "area", nullable = false)
    private Double area;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "status", nullable = false)
    private String status;

    @Lob
    @Column(name = "note",length = 999999999)
    private String note;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "province_code", nullable = false)
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_code", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_code",nullable = false)
    private Ward ward;

    @OneToOne(mappedBy = "realEstate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private RealEstateDetail realEstateDetail;

    @Size(max = 15)
    @NotNull
    @Column(name = "code", nullable = false, length = 15)
    private String code;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "real_estate_id")
    private Set<Image> images;

    @NotNull
    @ColumnDefault("0")
    @Column(name= "is_active", nullable = false)
    private boolean isActive = false;
}
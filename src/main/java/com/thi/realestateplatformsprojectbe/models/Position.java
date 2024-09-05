package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "positions", schema = "real_estate_platform")
public class Position {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 155)
    @NotNull
    @Column(name = "name", nullable = false, length = 155)
    private String name;

}
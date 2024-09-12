package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
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
    private Long id;

    @Size(max = 155)
    @NotNull
    @Column(name = "name", nullable = false, length = 155)
    private String name;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
}
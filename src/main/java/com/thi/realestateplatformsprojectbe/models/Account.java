package com.thi.realestateplatformsprojectbe.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "accounts", schema = "real_estate_platform")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 155)
    @NotNull
    @Column(name = "account_name", nullable = false, length = 155)
    private String accountName;

    @Size(max = 155)
    @NotNull
    @Column(name = "password", nullable = false, length = 155)
    private String password;

}
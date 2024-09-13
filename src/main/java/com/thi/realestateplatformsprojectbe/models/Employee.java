package com.thi.realestateplatformsprojectbe.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees", schema = "real_estate_platform")
@Builder
public class Employee implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 55)
    @NotNull
    @Column(name = "code", nullable = false, length = 55)
    private String code;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Size(max = 10)
    @NotNull
    @Column(name = "gender", nullable = false, length = 10)
    private String gender;

    @Size(max = 255)
    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 10)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonBackReference
    private Account account;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
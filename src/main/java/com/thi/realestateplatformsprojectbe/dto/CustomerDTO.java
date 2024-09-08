package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class CustomerDTO {

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 255)
    private String password;

    @NotNull
    @Size(max = 155)
    private String name;

    @NotNull
    private LocalDate dob;

    @NotNull
    @Size(max = 10)
    private String gender;

    @NotNull
    @Size(max = 255)
    private String address;

    @NotNull
    @Size(max = 10)
    private String phoneNumber;

    @NotNull
    @Size(max = 12)
    private String idCard;

    @NotNull
    private String customerType;
}
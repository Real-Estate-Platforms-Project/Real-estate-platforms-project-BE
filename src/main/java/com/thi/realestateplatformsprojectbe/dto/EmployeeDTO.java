package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDTO {


    @NotBlank(message = "Tên là bắt buộc")
    private String name;

    private LocalDate dob;

    @NotBlank(message = "Giới tính là bắt buộc")
    private String gender;

    @NotBlank(message = "Địa chỉ là bắt buộc")
    private String address;

    @Email(message = "Email phải hợp lệ và có @gmail.com")
    @NotBlank(message = "Email là bắt buộc")
    private String email;

    @NotBlank(message = "Số điện thoại bắt buộc nhập")
    private String phoneNumber;

    private String role;

    private Long positionId;
}
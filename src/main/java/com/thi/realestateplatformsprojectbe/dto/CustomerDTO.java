package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {

    @NotNull(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email phải có đuôi @gmail.com")
    @Size(max = 100, message = "Email không được vượt quá 100 ký tự")
    private String email;

    @NotNull(message = "Tên không được để trống")
    @Size(min = 2, max = 155, message = "Tên phải có độ dài từ 2 đến 155 ký tự")
    private String name;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate dob;

    @NotNull(message = "Giới tính không được để trống")
    @Size(max = 10, message = "Giới tính không được vượt quá 10 ký tự")
    private String gender;

    @NotNull(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại phải có đúng 10 chữ số")
    private String phoneNumber;

    @NotNull(message = "ID card không được để trống")
    @Pattern(regexp = "^[0-9]{9,12}$", message = "ID card phải có từ 9 đến 12 chữ số")
    private String idCard;

    @NotNull(message = "Loại khách hàng không được để trống")
    @Pattern(regexp = "^(seller|buyer)$", message = "Loại khách hàng phải là ROLE_SELLER hoặc ROLE_BUYER")
    private String customerType;

    @Size(max = 255, message = "URL của ảnh không được vượt quá 255 ký tự")
    private String imageUrl;
}
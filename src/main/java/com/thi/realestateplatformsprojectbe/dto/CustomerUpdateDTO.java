package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerUpdateDTO {
    @NotNull(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Định dạng email không hợp lệ.")
    @Size(max = 155, message = "Email không được vượt quá 155 ký tự")
    private String email;

    @NotNull(message = "Tên không được để trống")
    @Size(min = 2, max = 155, message = "Tên phải có độ dài từ 2 đến 155 ký tự")
    private String name;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là một ngày trong quá khứ")
    private LocalDate dob;

    @NotNull(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Địa chỉ không được để trống")
    @Size(max = 255, message = "Địa chỉ không được vượt quá 255 ký tự")
    private String address;

    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^([+0])\\d{9,12}$", message = "Số điện thoại không hợp lệ.")
    private String phoneNumber;

    @NotNull(message = "ID card không được để trống")
    @Pattern(regexp = "^(?:\\d{12}|[A-Z0-9]{8}|[A-Z][0-9]{8})$", message = "CCCD/Hộ chiếu không hợp lệ.")
    private String idCard;

    private String imageUrl;
}

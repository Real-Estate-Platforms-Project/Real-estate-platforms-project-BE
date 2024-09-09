package com.thi.realestateplatformsprojectbe.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountDTO {
    @Size(max = 255)
    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên người dùng không được để trống!")
    @Size(min = 3, message = "Tên người dùng ít nhấ 3 ký tự!")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Định dạng email không hợp lệ!")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống!")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống!")
    private String confirmPassword;
}

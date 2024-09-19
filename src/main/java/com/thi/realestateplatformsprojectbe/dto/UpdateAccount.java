package com.thi.realestateplatformsprojectbe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateAccount {


    private String recentPassWord;

    @NotNull(message = "Nhập mật khẩu mới")
    @Size(min = 6,message = "Mật khẩu tối thiểu 6 ký tự")
    private String newPassWord;

    @NotNull(message = "Nhập xác nhận mật khẩu")
    private String reEnterPassWord;

}
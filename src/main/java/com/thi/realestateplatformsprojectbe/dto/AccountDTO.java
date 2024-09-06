package com.thi.realestateplatformsprojectbe.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String email;
    private String password;
    private String confirmPassword;
}

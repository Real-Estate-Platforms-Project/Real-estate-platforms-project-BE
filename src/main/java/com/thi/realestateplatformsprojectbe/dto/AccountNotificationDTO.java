package com.thi.realestateplatformsprojectbe.dto;

import com.thi.realestateplatformsprojectbe.models.Account;
import lombok.Data;

@Data
public class AccountNotificationDTO {
    private String name;
    private boolean reading;
    private Account account;
}

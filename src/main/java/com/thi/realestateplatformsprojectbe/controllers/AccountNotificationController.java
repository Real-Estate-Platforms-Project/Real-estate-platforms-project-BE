package com.thi.realestateplatformsprojectbe.controllers;

import com.thi.realestateplatformsprojectbe.configs.UserPrinciple;
import com.thi.realestateplatformsprojectbe.models.AccountNotification;
import com.thi.realestateplatformsprojectbe.models.RealEstate;
import com.thi.realestateplatformsprojectbe.services.IAccountNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/notification")
public class AccountNotificationController {

    @Autowired
    private IAccountNotificationService accountNotificationService;

    @GetMapping("")
    public ResponseEntity<List<AccountNotification>> findAllNotificationByAccountId(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        List<AccountNotification> accountNotifications = accountNotificationService.findAllByAccountId(userPrinciple.getAccountId());
        return new ResponseEntity<>(accountNotifications, HttpStatus.OK);
    }
}

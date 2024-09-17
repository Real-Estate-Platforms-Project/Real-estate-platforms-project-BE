package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.dto.RealEstateWithDetailDTO;
import com.thi.realestateplatformsprojectbe.models.AccountNotification;

import java.util.List;

public interface IAccountNotificationService {
    void addNotification(RealEstateWithDetailDTO realEstateWithDetailDTO);
    List<AccountNotification> findAllByAccountId(Long accountId);
}

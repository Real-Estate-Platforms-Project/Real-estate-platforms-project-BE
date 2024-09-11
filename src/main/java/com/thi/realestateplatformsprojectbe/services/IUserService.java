package com.thi.realestateplatformsprojectbe.services;

import com.thi.realestateplatformsprojectbe.models.User;

public interface IUserService {
    String generateBuyerCode();

    void save(User user);

    User getByAccountId(Long accountId);
}

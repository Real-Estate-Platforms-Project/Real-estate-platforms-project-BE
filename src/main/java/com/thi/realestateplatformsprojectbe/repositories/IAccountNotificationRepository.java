package com.thi.realestateplatformsprojectbe.repositories;

import com.thi.realestateplatformsprojectbe.models.AccountNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAccountNotificationRepository extends JpaRepository<AccountNotification, Long> {
    List<AccountNotification> findAllByAccountId(Long id);
}

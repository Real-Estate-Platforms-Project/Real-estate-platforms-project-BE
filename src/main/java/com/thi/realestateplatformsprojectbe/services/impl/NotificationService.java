package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Notification;
import com.thi.realestateplatformsprojectbe.repositories.INotificationRepository;
import com.thi.realestateplatformsprojectbe.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }
}

package com.thi.realestateplatformsprojectbe.services;


import com.thi.realestateplatformsprojectbe.models.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> findAll();

    void save(Notification notification);
}

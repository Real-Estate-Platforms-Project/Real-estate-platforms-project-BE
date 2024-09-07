package com.thi.realestateplatformsprojectbe.services;


import com.thi.realestateplatformsprojectbe.models.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> findAll();

    void save(Notification notification);

    List<Notification> findAllNotificationByTitle(String title);

    Notification findNotificationById(int id);

    Notification saveNotification(Notification notification);

    Notification updateNotification(int id, Notification notification);

    boolean deleteNotification(int id);
}

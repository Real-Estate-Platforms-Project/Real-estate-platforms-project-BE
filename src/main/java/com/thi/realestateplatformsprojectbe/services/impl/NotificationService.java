package com.thi.realestateplatformsprojectbe.services.impl;

import com.thi.realestateplatformsprojectbe.models.Notification;
import com.thi.realestateplatformsprojectbe.models.NotificationImage;
import com.thi.realestateplatformsprojectbe.repositories.INotificationImageRepository;
import com.thi.realestateplatformsprojectbe.repositories.INotificationRepository;
import com.thi.realestateplatformsprojectbe.services.INotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private INotificationRepository notificationRepository;

    @Autowired
    private INotificationImageRepository notificationImageRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);

    }

    @Override
    public List<Notification> findAllNotificationByTitle(String title) {
        return notificationRepository.findAllNotificationByTitleContaining(title);
    }

    @Override
    public Notification findNotificationById(int id) {
        return notificationRepository.findNotificationById(id);
    }

    @Override
    public Notification saveNotification(Notification notification) {
        Notification savedNotification = notificationRepository.save(notification);
        if (notification.getImages() != null) {
            for (NotificationImage image : notification.getImages()) {
                if (savedNotification != null) {
                    image.setNotification(savedNotification);
                }
                notificationImageRepository.save(image);
            }
        }

        return savedNotification;
    }

    @Override
    public Notification updateNotification(int id, Notification notification) {
        if (notificationRepository.existsById(id)) {
            notification.setId(id);
            Notification updatedNotification = notificationRepository.save(notification);

            if (notification.getImages() != null) {
                notificationImageRepository.deleteByNotificationId(id);

                // Lưu các ảnh mới
                for (NotificationImage image : notification.getImages()) {
                    image.setNotification(updatedNotification);
                    notificationImageRepository.save(image);
                }
            }
            return updatedNotification;
        }
        return null;
    }


    @Override
    public boolean deleteNotification(int id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

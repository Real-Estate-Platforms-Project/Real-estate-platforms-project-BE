package com.thi.realestateplatformsprojectbe.repositories;


import com.thi.realestateplatformsprojectbe.models.NotificationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface INotificationImageRepository extends JpaRepository<NotificationImage, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM real_estate_platform.notification_images WHERE notification_id = :notificationId", nativeQuery = true)
    void deleteByNotificationId(@Param("notificationId") Integer notificationId);
}

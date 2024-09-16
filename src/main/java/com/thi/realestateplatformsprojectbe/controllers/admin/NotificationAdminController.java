package com.thi.realestateplatformsprojectbe.controllers.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thi.realestateplatformsprojectbe.dto.NotificationDTO;
import com.thi.realestateplatformsprojectbe.models.Notification;
import com.thi.realestateplatformsprojectbe.services.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
@RequestMapping("api/admin/notifications")
public class NotificationAdminController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        try {
            Notification createdNotification = notificationService.saveNotification(notification);
            NotificationDTO notificationDTO = new NotificationDTO(createdNotification.getEmployee().getId(), createdNotification.getTitle());
            String notificationMessage = new ObjectMapper().writeValueAsString(notificationDTO);
            messagingTemplate.convertAndSend("/topic/notifications", notificationMessage);
            return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") int id, @RequestBody Notification notification) {
        Notification updatedNotification = notificationService.updateNotification(id, notification);
        if (updatedNotification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") int id) {
        boolean isDeleted = notificationService.deleteNotification(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

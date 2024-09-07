package com.thi.realestateplatformsprojectbe.controllers.client;

import com.thi.realestateplatformsprojectbe.models.Notification;
import com.thi.realestateplatformsprojectbe.services.INotificationService;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin("*")
@RequestMapping("api/client/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotificationByTitle(@RequestParam(value = "title_like", defaultValue = "") String title) {
        List<Notification> notificationList = notificationService.findAllNotificationByTitle(title);
        PrettyTime prettyTime = new PrettyTime(new Locale("vi"));
        for (Notification notification : notificationList) {
            Instant instant = notification.getCreateAt().atZone(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            notification.setFormattedCreateNotification(prettyTime.format(date));
        }

        if (notificationList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable("id") int id) {
        Notification notification = notificationService.findNotificationById(id);
        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }
}

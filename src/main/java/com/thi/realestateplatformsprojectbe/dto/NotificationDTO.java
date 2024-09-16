package com.thi.realestateplatformsprojectbe.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private String title;

    public NotificationDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}

package com.ecommerce.userservice.domain.model;

import java.time.LocalDateTime;

public class Notification {
    private Long id;
    private Long userId;

    private String title;
    private String message;
    private String type; // e.g. ORDER, SYSTEM, PROMO

    private boolean isRead;
    private LocalDateTime createdAt;

    public void markAsRead() {
        this.isRead = true;
    }
}

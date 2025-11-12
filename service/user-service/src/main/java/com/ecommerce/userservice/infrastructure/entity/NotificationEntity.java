package com.ecommerce.userservice.infrastructure.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;
    private String message;
    private String type; // INFO, ORDER, PROMO, etc.

    private boolean isRead;

    private LocalDateTime createdAt;
}


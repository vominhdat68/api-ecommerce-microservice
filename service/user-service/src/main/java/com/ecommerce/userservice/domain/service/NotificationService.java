package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotifications(Long userId);
    void markAllAsRead(Long userId);
    void deleteNotification(Long id);
}


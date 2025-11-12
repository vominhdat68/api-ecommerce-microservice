package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    List<Notification> findByUserId(Long userId);
    Optional<Notification> findByIdAndUserId(Long id, Long userId);
    void markAllAsRead(Long userId);
    void deleteByIdAndUserId(Long id, Long userId);
    Notification save(Notification notification);
}


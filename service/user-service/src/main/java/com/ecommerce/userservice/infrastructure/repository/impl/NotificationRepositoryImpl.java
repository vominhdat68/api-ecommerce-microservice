package com.ecommerce.userservice.infrastructure.repository.impl;


import com.ecommerce.userservice.domain.model.Notification;
import com.ecommerce.userservice.domain.repository.NotificationRepository;
import com.ecommerce.userservice.infrastructure.repository.JpaNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JpaNotificationRepository jpa;

    @Override
    public List<Notification> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Optional<Notification> findByIdAndUserId(Long id, Long userId) {
        return Optional.empty();
    }

    @Override
    public void markAllAsRead(Long userId) {

    }

    @Override
    public void deleteByIdAndUserId(Long id, Long userId) {

    }

    @Override
    public Notification save(Notification notification) {
        return null;
    }

    // Ánh xạ các phương thức tương tự như trên
}

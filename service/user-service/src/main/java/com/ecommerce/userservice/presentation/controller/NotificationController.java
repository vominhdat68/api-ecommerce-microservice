package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.response.NotificationResponse;
import com.ecommerce.userservice.domain.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getNotifications() {

        return null;
    }

    @PostMapping("/mark-as-read")
    public void markAsRead() {}

    @DeleteMapping("/{id}")
    public void deleteNotification(@PathVariable Long id) {}
}

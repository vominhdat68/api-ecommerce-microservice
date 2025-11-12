package com.ecommerce.productservice.domain.model;

import java.time.LocalDateTime;

public class Banner {
    private Long id;
    private String title;
    private String imageUrl;
    private String targetUrl; // URL khi click vào banner
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;

    // Domain Logic
    public boolean isCurrentlyActive() {
        LocalDateTime now = LocalDateTime.now();
        return isActive &&
                !now.isBefore(startDate) &&
                !now.isAfter(endDate);
    }

    public void activate() {
        if (startDate == null || endDate == null) {
            throw new IllegalStateException("Start/End date must be set before activation");
        }
        this.isActive = true;
    }

    public void deactivate() {
        this.isActive = false;
    }
}


package com.ecommerce.userservice.domain.model;

import java.time.LocalDateTime;

public class UserLog {
    private Long id;
    private Long userId;

    private String action;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime timestamp;
}

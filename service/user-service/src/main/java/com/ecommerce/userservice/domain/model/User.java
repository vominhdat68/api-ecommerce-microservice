package com.ecommerce.userservice.domain.model;

import java.time.LocalDate;

public class User {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String avatarUrl;
    private boolean isActive;

    // Business method ví dụ:
    public void deactivate() {
        this.isActive = false;
    }

    public boolean isProfileCompleted() {
        return fullName != null && phoneNumber != null && dateOfBirth != null;
    }
}

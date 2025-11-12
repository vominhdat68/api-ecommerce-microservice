package com.ecommerce.userservice.infrastructure.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_addresses")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String fullName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String street;

    private boolean isDefault;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

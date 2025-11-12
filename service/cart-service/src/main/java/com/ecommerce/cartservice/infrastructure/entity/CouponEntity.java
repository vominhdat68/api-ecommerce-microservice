package com.ecommerce.cartservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private double discountAmount;

    private double minimumOrder;

    private LocalDateTime validFrom;

    private LocalDateTime validTo;

    private boolean isActive;

    // Getters, Setters, Constructors
}

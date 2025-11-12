package com.ecommerce.userservice.infrastructure.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recently_viewed_products")
public class RecentlyViewedProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;

    private String productName;
    private String productImage;
    private BigDecimal price;

    private LocalDateTime viewedAt;
}

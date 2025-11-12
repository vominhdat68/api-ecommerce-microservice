package com.ecommerce.userservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecentlyViewedProduct {
    private Long id;
    private Long userId;
    private Long productId;

    private String productName;
    private String productImage;
    private BigDecimal price;

    private LocalDateTime viewedAt;
}


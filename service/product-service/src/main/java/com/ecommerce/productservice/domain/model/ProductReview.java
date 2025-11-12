package com.ecommerce.productservice.domain.model;

import java.time.LocalDateTime;

public class ProductReview {
    private Long id;
    private Long productId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;


}


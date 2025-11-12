package com.ecommerce.cartservice.application.dto.request;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
    private Long productId;
    private Integer quantity;

    // Getters and setters
}
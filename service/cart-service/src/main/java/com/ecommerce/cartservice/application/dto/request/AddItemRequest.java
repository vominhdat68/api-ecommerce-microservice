package com.ecommerce.cartservice.application.dto.request;

import lombok.Data;

@Data
public class AddItemRequest {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;

    // Getters and setters
}


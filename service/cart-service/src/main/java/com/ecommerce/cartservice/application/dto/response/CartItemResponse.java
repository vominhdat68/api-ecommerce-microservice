package com.ecommerce.cartservice.application.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartItemResponse {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double unitPrice;
    private Double totalPrice;

    // Getters and setters
}

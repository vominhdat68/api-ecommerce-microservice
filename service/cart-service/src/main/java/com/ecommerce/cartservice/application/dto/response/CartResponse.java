package com.ecommerce.cartservice.application.dto.response;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CartResponse {
    private Long userId;
    private List<CartItemResponse> items;
    private String note;
    private String couponCode;
    private String shippingMethod;

    // Getters and setters
}

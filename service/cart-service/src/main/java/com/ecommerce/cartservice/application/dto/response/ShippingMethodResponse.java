package com.ecommerce.cartservice.application.dto.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShippingMethodResponse {
    private String code;
    private String name;
    private Double fee;

    // Getters and setters
}
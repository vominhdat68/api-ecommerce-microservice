package com.ecommerce.cartservice.application.dto.response;

import lombok.Builder;

@Builder
public class EstimatedCostResponse {
    private Double subtotal;
    private Double shippingFee;
    private Double tax;
    private Double discount;
    private Double total;

    // Getters and setters
}
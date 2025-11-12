package com.ecommerce.cartservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
public class CartItem {
    private Long productId;
    private String name;
    private int quantity;
    private double unitPrice;

    public CartItem(Long productId, String name, int quantity, double unitPrice) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void increaseQuantity(int delta) {
        this.quantity += delta;
    }

    public double getTotalPrice() {
        return unitPrice * quantity;
    }
}

package com.ecommerce.cartservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
public class Cart {
    private Long userId;
    private List<CartItem> items = new ArrayList<>();
    private Coupon appliedCoupon;
    private String note;
    private ShippingMethod shippingMethod;

    public Cart() {
    }
    public Cart(Long userId) {
        this.userId = userId;
    }

    public void addItem(CartItem item) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(item.getProductId()))
                .findFirst();
        if (existing.isPresent()) {
            existing.get().increaseQuantity(item.getQuantity());
        } else {
            items.add(item);
        }
    }

    public void updateItemQuantity(Long productId, int quantity) {
        items.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void clear() {
        items.clear();
    }

    public EstimatedCost calculateEstimatedCost() {
        double subtotal = 0.0;

        for (CartItem item : items) {
            subtotal += item.getQuantity() * item.getUnitPrice();
        }

        double tax = subtotal * 0.1; // 10% VAT
        double shippingFee = shippingMethod != null ? shippingMethod.getFee() : 0.0;
        double discount = (appliedCoupon != null) ? appliedCoupon.calculateDiscount(subtotal) : 0.0;

        return new EstimatedCost(subtotal, discount, tax, shippingFee);
    }


    public void setCoupon(Coupon coupon) {
        this.appliedCoupon = coupon;
    }

    public void removeCoupon() {
        this.appliedCoupon = null;
    }


}

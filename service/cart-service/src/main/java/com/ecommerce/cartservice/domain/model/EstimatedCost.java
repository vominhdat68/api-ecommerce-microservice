package com.ecommerce.cartservice.domain.model;


import lombok.Data;

@Data
public class EstimatedCost {
    private double subtotal;
    private double discount;
    private double tax;
    private double shippingFee;
    private double total;

    public EstimatedCost(double subtotal, double discount, double tax, double shippingFee) {
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.shippingFee = shippingFee;
        this.total = subtotal - discount + tax + shippingFee;
    }


}

package com.ecommerce.cartservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Coupon {
    private String code;
    private double discountPercent ;

    public Coupon(String code ) {
        this.code = code;
    }

    public Coupon(String code, double discountPercent ) {
        this.code = code;
        this.discountPercent  = discountPercent ;
    }

    public double calculateDiscount(double subtotal) {
        return subtotal*(discountPercent /100.0) ;
    }
}


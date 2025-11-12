package com.ecommerce.cartservice.domain.model;


import lombok.Data;

@Data
public class ShippingMethod {
    private String code;
    private String name;
    private double fee;

    public ShippingMethod(String code, String name, double fee) {
        this.code = code;
        this.name = name;
        this.fee = fee;
    }
}

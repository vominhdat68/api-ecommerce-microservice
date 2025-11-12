package com.ecommerce.cartservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shipping_methods")
public class ShippingMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private double fee;

    // Getters, Setters, Constructors
}
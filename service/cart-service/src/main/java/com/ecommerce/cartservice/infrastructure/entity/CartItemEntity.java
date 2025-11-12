package com.ecommerce.cartservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private String productName;

    private double price;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private CartEntity cart;

    // Getters, Setters, Constructors
}


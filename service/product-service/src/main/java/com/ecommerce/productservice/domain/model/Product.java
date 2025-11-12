package com.ecommerce.productservice.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private int stockQuantity;
    private String sku;
    private Category category;
    private Brand brand;
    private boolean featured;
    private boolean active;
//    private Set<Review> reviews;
    private Set<ProductAttribute> attributes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean isInStock() {
        return stockQuantity > 0;
    }

    public boolean hasDiscount() {
        return discountPrice != null && discountPrice.compareTo(BigDecimal.ZERO) > 0
                && discountPrice.compareTo(price) < 0;
    }

    public BigDecimal getCurrentPrice() {
        return hasDiscount() ? discountPrice : price;
    }

    public void addAttribute(ProductAttribute attribute) {
        attributes.add(attribute);
    }
}
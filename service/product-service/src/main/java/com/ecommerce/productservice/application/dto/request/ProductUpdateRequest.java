package com.ecommerce.productservice.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    @Size(max = 255, message = "Product name must be less than 255 characters")
    private String name;

    @Size(max = 2000, message = "Description must be less than 2000 characters")
    private String description;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 digits and 2 decimal places")
    private BigDecimal price;

    @DecimalMin(value = "0.0", message = "Discount price must be 0 or greater")
    @Digits(integer = 10, fraction = 2, message = "Discount price must have up to 10 digits and 2 decimal places")
    private BigDecimal discountPrice;

    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    @Size(max = 100, message = "SKU must be less than 100 characters")
    private String sku;

    private Long categoryId;
    private Long brandId;
    private Boolean featured;
    private Boolean active;
    private Map<Long, String> attributes;
}
package com.ecommerce.productservice.infrastructure.repository.projection;

import java.math.BigDecimal;

public interface ProductProjection {
    Long getId();
    String getName();
    BigDecimal getPrice();
    BigDecimal getDiscountPrice();
    Integer getStockQuantity();
}

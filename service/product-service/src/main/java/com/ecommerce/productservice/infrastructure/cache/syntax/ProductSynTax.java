package com.ecommerce.productservice.infrastructure.cache.syntax;

import java.time.Duration;

public class ProductSynTax {
    public static final String PRODUCT_BY_ID = "productById";



    private static final Duration DEFAULT_TTL = Duration.ofMinutes(5);
}

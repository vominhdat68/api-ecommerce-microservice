package com.ecommerce.productservice.domain.model;


import java.math.BigDecimal;
import java.util.Set;

public class ProductSearchCriteria {
    private String query;
    private Long categoryId;
    private Long brandId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Set<String> attributes;
    private Boolean isFeatured;
    private Boolean isActive;
    private String sortBy;
    private String sortDirection;
//    Pageable pageable;
}

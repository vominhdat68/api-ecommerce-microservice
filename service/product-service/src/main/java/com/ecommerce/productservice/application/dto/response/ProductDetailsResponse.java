package com.ecommerce.productservice.application.dto.response;

import com.ecommerce.productservice.domain.model.Attribute;
import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.ProductAttribute;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailsResponse {
    private Long id;
    private String name;
    private String slug; //them '-' vao space name
    private String description;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private BigDecimal currentPrice;
    private Integer stockQuantity;
    private String sku; //mã hàng
    private Double averageRating;
    private Integer reviewCount;

    private CategoryResponse category;
    private BrandResponse brand;
    private Map<String, List<String>> attributes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Boolean is_featured;
    private Boolean is_active;
    private Boolean in_stock;
    private Boolean has_discount;

}

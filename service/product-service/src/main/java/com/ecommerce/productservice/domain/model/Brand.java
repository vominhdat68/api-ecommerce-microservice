package com.ecommerce.productservice.domain.model;

import lombok.*;
import org.springframework.data.domain.Page;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Long id;
    private String name;
    private String slug;
    private String logoUrl;
    private String website;
    private boolean isFeatured;
    private Page<Product> products;
}
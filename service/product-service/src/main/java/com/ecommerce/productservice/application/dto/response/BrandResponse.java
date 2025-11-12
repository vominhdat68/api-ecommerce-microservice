package com.ecommerce.productservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse {
    private Long id;
    private String name;
    private String slug;
    private String logoUrl;
    private String website;
    private Boolean featured;
    private Integer productCount;
    private Page<ProductDetailsResponse> products;

    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
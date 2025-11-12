package com.ecommerce.productservice.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private Integer level;
    private String imageUrl;
    private Boolean active;
    private Integer productCount;
    private List<CategoryResponse> sub_categories;
    private Page<ProductDetailsResponse> products;

    public CategoryResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
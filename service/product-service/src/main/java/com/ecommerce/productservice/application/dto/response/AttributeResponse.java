package com.ecommerce.productservice.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttributeResponse {
    private Long id;
    private String name;
    private Boolean filterable;
    private Long usageCount;
}

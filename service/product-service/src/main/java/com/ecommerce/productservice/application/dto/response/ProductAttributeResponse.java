package com.ecommerce.productservice.application.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributeResponse {
    private Long attributeId;
    private String attributeName;
    private String value;
    private Boolean filterable;
}

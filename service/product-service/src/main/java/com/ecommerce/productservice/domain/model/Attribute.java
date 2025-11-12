package com.ecommerce.productservice.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attribute{
        private Long id;
        private String name;
        private boolean filterable;
}
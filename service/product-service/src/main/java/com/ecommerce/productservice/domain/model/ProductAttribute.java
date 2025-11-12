package com.ecommerce.productservice.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttribute {
        private Long id;
        private Product product;
        private Attribute attribute;
        private String value;

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                ProductAttribute that = (ProductAttribute) o;
                return product.getId().equals(that.product.getId()) &&
                        attribute.getId().equals(that.attribute.getId());
        }

//        @Override
//        public int hashCode() {
//                return 31 * product.getId().hashCode() + attribute.getId().hashCode();
//        }
}
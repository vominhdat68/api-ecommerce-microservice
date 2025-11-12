package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.domain.model.ProductAttribute;
import com.ecommerce.productservice.infrastructure.entity.ProductAttributeEntity;

import java.util.Set;

public class ProductAttributeMapper {


    public static ProductAttribute toModel(ProductAttributeEntity attribute) {
        ProductAttribute model = new ProductAttribute();
        model.setId(attribute.getId());
        model.setAttribute(AttributesMapper.toModel(attribute.getAttribute()));
        model.setValue(attribute.getValue());

        return model;
    }
}

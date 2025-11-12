package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.domain.model.Attribute;
import com.ecommerce.productservice.infrastructure.entity.AttributeEntity;

public class AttributesMapper {
    public static Attribute toModel(AttributeEntity attribute) {
        Attribute model = new Attribute();
        model.setId(attribute.getId());
        model.setName(attribute.getName());
        return model;
    }
}

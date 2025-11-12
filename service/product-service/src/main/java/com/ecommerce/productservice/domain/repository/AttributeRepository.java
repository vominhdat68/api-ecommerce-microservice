package com.ecommerce.productservice.domain.repository;


import com.ecommerce.productservice.domain.model.Attribute;

import java.util.List;
import java.util.Optional;

public interface AttributeRepository {
    Attribute save(Attribute attribute);
    Optional<Attribute> findById(Long id);
    List<Attribute> findAllFilterableAttributes();
    List<String> findDistinctValuesByAttributeId(Long attributeId);
    Optional<Attribute> findByName(String name);
}
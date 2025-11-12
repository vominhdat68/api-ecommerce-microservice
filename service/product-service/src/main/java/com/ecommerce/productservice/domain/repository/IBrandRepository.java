package com.ecommerce.productservice.domain.repository;

import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.infrastructure.repository.projection.BrandProjection;

import java.util.List;

public interface IBrandRepository {
    List<BrandProjection> findByNameContainingIgnoreCase(String name);

    Brand getBrandById(Long id);
}
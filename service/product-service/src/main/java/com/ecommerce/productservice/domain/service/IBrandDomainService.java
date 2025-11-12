package com.ecommerce.productservice.domain.service;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.domain.model.Brand;

import java.util.List;

public interface IBrandDomainService {
    List<BrandResponse> findByNameContainingIgnoreCase(String brandName);

    Brand getBrandById(Long id);
}

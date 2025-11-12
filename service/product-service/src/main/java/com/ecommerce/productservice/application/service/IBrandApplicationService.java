package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.response.BrandResponse;

import java.util.List;

public interface IBrandApplicationService {
    List<BrandResponse> getBrandsByName(String brandName);

    BrandResponse getProductsByBrand(Long id, int page, int limit, String sort);
}

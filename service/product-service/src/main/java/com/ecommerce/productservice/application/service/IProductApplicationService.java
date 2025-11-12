package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;

public interface IProductApplicationService {
    ProductDetailsResponse getProfileProduct(Long id);

    ReviewProductResponse getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating);
}

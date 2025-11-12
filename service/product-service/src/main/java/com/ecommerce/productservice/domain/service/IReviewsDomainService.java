package com.ecommerce.productservice.domain.service;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.dto.response.ReviewResponse;

public interface IReviewsDomainService {
    ReviewProductResponse getReviewsProductFirstLoad(Long productId, Integer page, Integer limit, String sortBy);
    ReviewProductResponse getReviewsProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating);

    ReviewProductOrderResponse addReview(ReviewCreateRequest request);

    ReviewProductOrderResponse updateReview(Long id, ReviewCreateRequest request);

    void deleteReview(Long id);
}

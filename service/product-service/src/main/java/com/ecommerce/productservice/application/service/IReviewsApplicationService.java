package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.dto.response.ReviewResponse;

public interface IReviewsApplicationService {

    ReviewProductResponse getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating);

    ReviewProductOrderResponse addReview(ReviewCreateRequest reviewDto);

    ReviewProductOrderResponse updateReview(Long id, ReviewCreateRequest reviewDto);

    void deleteReview(Long id);
}

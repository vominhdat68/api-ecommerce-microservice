package com.ecommerce.productservice.application.service.impl;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.dto.response.ReviewResponse;
import com.ecommerce.productservice.application.service.IReviewsApplicationService;
import com.ecommerce.productservice.domain.service.IReviewsDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewsApplicationService implements IReviewsApplicationService {
    private final IReviewsDomainService iReviewsDomainService;

    @Override
    public ReviewProductResponse getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating) {
       if(starRating == null){
          return iReviewsDomainService.getReviewsProductFirstLoad(productId,page,limit,sortBy); // five star
       }

        return iReviewsDomainService.getReviewsProduct(productId,page,limit,sortBy,starRating);
    }

    @Override
    public ReviewProductOrderResponse addReview(ReviewCreateRequest request) {

        return iReviewsDomainService.addReview(request);
    }

    @Override
    public ReviewProductOrderResponse updateReview(Long id, ReviewCreateRequest request) {
        return iReviewsDomainService.updateReview(id,request);
    }

    @Override
    public void deleteReview(Long id) {
        iReviewsDomainService.deleteReview(id);
    }
}

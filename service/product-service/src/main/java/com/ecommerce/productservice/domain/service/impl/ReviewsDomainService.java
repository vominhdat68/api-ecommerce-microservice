package com.ecommerce.productservice.domain.service.impl;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.dto.response.ReviewResponse;
import com.ecommerce.productservice.application.mapper.ReviewMapper;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.model.Review;
import com.ecommerce.productservice.domain.repository.IProductDomainRepository;
import com.ecommerce.productservice.domain.repository.IReviewRepository;
import com.ecommerce.productservice.domain.service.IReviewsDomainService;
import com.ecommerce.productservice.exception.BusinessException;
import com.ecommerce.productservice.infrastructure.entity.ReviewEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductFullStatus;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewsDomainService implements IReviewsDomainService {
    private final IReviewRepository iReviewRepository;
    private final IProductDomainRepository iProductDomainRepository;

    @Override
    public ReviewProductResponse getReviewsProductFirstLoad(Long productId, Integer page, Integer limit, String sortBy) {
        ProductFullStatus statusProduct = iReviewRepository.getProductStats(productId);
        Page<Review> reviewPage = iReviewRepository.getReviewsByProductFirstLoad(productId,page,limit,sortBy);
        return ReviewMapper.toModel(reviewPage,statusProduct);
    }


    @Override
    public ReviewProductResponse getReviewsProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating) {
        Page<Review> reviewPage = iReviewRepository.getReviewsByProduct(productId,page,limit,sortBy,starRating);
        return ReviewMapper.toModel(reviewPage,null);
    }

    @Override
    public ReviewProductOrderResponse addReview(ReviewCreateRequest request) {
        // call check order product user - chi nguoi da mua san pham moi dc danh gia

        if (iReviewRepository.existsByProductIdAndUserId(request.getUserId(), request.getProductId())) {
            throw new BusinessException("You have already reviewed this product");
        }
        Review response = iReviewRepository.addReview(ReviewMapper.toEntity(request));
        return ReviewMapper.toResponseReviewOrder(response);
    }

    @Override
    public ReviewProductOrderResponse updateReview(Long id, ReviewCreateRequest request) {
        ReviewEntity entity = iReviewRepository.findReviewEntityById(id).orElseThrow();
        entity.setRating(request.getRating());
        entity.setComment(request.getComment());
        ReviewProductOrderResponse response = ReviewMapper.toResponseReviewOrder(iReviewRepository.updateReview(entity));
        return response;
    }

    @Override
    public void deleteReview(Long id) {
        iReviewRepository.findReviewEntityById(id).ifPresent(review -> iReviewRepository.deleteReview(id));
    }
}

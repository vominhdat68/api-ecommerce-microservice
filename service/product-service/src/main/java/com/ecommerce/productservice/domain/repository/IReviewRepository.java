package com.ecommerce.productservice.domain.repository;

import com.ecommerce.productservice.domain.model.Review;
import com.ecommerce.productservice.infrastructure.entity.ReviewEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductFullStatus;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IReviewRepository {
    Page<Review> getReviewsByProductFirstLoad(Long productId, Integer page, Integer limit, String sortBy);
    Page<Review> getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating);
    ProductFullStatus getProductStats( Long productId);
    Review addReview(ReviewEntity entity);
    Review updateReview(ReviewEntity entity);
    void deleteReview(Long id);
    boolean existsByProductIdAndUserId(Long userId, Long productId);
    Optional<ReviewEntity> findReviewEntityById(Long id);
}
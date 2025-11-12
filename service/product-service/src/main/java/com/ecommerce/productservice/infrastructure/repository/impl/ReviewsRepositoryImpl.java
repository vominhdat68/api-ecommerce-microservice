package com.ecommerce.productservice.infrastructure.repository.impl;


import com.ecommerce.productservice.application.dto.response.ReviewResponse;
import com.ecommerce.productservice.application.mapper.ReviewMapper;
import com.ecommerce.productservice.domain.model.Review;
import com.ecommerce.productservice.domain.repository.IReviewRepository;
import com.ecommerce.productservice.infrastructure.entity.ReviewEntity;
import com.ecommerce.productservice.infrastructure.repository.JpaReviewsRepository;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductFullStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ReviewsRepositoryImpl implements IReviewRepository {
    private final JpaReviewsRepository jpaReviewsRepository;


    @Override
    public Page<Review> getReviewsByProductFirstLoad(Long productId, Integer page, Integer limit, String sortBy) {
        return getReviewsByProduct(productId,page,limit,sortBy,5);
    }

    @Override
    public Page<Review> getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating) {
        PageRequest pageRequest = PageRequest.of(
                page != null ? page : 0,
                limit != null ? limit : 10,
                sortBy != null ? Sort.by(sortBy).descending() : Sort.unsorted()
        );

        Page<ReviewEntity> reviewEntities  = jpaReviewsRepository.findByProductIdAndRating(productId,starRating,pageRequest);
        List<Review> reviews = reviewEntities.getContent()
                .stream()
                .map(ReviewMapper::toModel)
                .toList();
        return new PageImpl<>(
                reviews,
                reviewEntities.getPageable(),
                reviewEntities.getTotalElements()
        );


    }

    @Override
    public ProductFullStatus getProductStats(Long productId) {
        ProductFullStatus status = jpaReviewsRepository.getFullStats(productId).orElseThrow();
        return status;
    }

    @Override
    public Review addReview(ReviewEntity entity) {
        Review review = ReviewMapper.toModel(jpaReviewsRepository.save(entity));
        return review;
    }

    @Override
    public Review updateReview(ReviewEntity entity) {
        Review review = ReviewMapper.toModel(jpaReviewsRepository.save(entity));
        return review;
    }

    @Override
    public void deleteReview(Long id) {
        jpaReviewsRepository.deleteById(id);
    }

    @Override
    public boolean existsByProductIdAndUserId(Long userId, Long productId) {
        return jpaReviewsRepository.existsByProductIdAndUserId(userId,productId);
    }

    @Override
    public Optional<ReviewEntity> findReviewEntityById(Long id) {
        return jpaReviewsRepository.findById(id);
    }


}
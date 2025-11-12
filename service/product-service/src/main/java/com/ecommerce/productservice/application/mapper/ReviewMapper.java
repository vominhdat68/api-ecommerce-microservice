package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.model.Review;
import com.ecommerce.productservice.infrastructure.entity.ProductEntity;
import com.ecommerce.productservice.infrastructure.entity.ReviewEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductFullStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public class ReviewMapper {

    public static Review toModel(ReviewEntity entity) {
        Product product = new Product();
        product.setId(entity.getProduct().getId());
        product.setName(entity.getProduct().getName());

        Review  model = new Review();
        model.setId(entity.getId());
        model.setUserId(entity.getUserId());
        model.setUerName("message queue");
        model.setRating(entity.getRating());
        model.setComment(entity.getComment());
        model.setApproved(entity.isApproved());
        model.setCreatedAt(entity.getCreatedAt());
        model.setImage("image user");

        return model;
    }


    public static ReviewProductResponse toModel(Page<Review> reviewPage, ProductFullStatus status) {
        ReviewProductResponse response = new ReviewProductResponse();

        if(status == null){
             response.setReviews(reviewPage);
             return response;
        }

        response.setProduct_id(status.getProduct_id());
        response.setProduct_name(status.getProduct_name());
        response.setAverage_rating(status.getAvg_rating());
        response.setTotal_reviews(status.getTotal());

        ReviewProductResponse.RatingDistribution ratingCount = new ReviewProductResponse.RatingDistribution();
        ratingCount.setStars_5(status.getFive_stars());
        ratingCount.setStars_4(status.getFour_stars());
        ratingCount.setStars_3(status.getThree_stars());
        ratingCount.setStars_2(status.getTwo_stars());
        ratingCount.setStars_1(status.getOne_star());
        response.setRating_distribution(ratingCount);
        response.setReviews(reviewPage);

        return response;
    }

    public static ReviewEntity toEntity(ReviewCreateRequest request) {
        ReviewEntity review = new ReviewEntity();
        ProductEntity p = new ProductEntity();p.setId(request.getProductId());
        review.setProduct(p);
        review.setUserId(request.getUserId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        return review;
    }

    public static ReviewProductOrderResponse toResponseReviewOrder(Review response) {
        ReviewProductOrderResponse r = new ReviewProductOrderResponse(response.getId(),"Review success");
        return r;
    }
}

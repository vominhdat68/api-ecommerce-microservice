package com.ecommerce.productservice.application.dto.response;

import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.model.Review;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewProductResponse {
    private Long id;
    private Long product_id;
    private String product_name;
    private Double average_rating;
    private Integer total_reviews;
    private RatingDistribution rating_distribution;
    private Page<Review> reviews;


    @Setter
    @Getter
    public static class RatingDistribution {
        private Integer stars_5;
        private Integer stars_4;
        private Integer stars_3;
        private Integer stars_2;
        private Integer stars_1;
    }


}

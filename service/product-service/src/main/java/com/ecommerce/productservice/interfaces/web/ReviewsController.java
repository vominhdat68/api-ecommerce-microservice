package com.ecommerce.productservice.interfaces.web;

import com.ecommerce.productservice.application.dto.request.ReviewCreateRequest;
import com.ecommerce.productservice.application.dto.response.ReviewProductOrderResponse;
import com.ecommerce.productservice.application.dto.response.ReviewResponse;
import com.ecommerce.productservice.application.service.IReviewsApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final IReviewsApplicationService reviewsApplicationService;

    @PostMapping
    public ResponseEntity<ReviewProductOrderResponse> addReview(@RequestBody ReviewCreateRequest reviewDto) {
        // Implementation to add a new review
        ReviewProductOrderResponse savedReview = reviewsApplicationService.addReview(reviewDto);
        return ResponseEntity.ok(savedReview);
    }

    // PUT /reviews/{id} – Update a review
    @PutMapping("/{id}")
    public ResponseEntity<ReviewProductOrderResponse> updateReview(
            @PathVariable Long id,
            @RequestBody ReviewCreateRequest reviewDto) {
        // Implementation to update an existing review
        ReviewProductOrderResponse updatedReview = reviewsApplicationService.updateReview(id, reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    // DELETE /reviews/{id} – Delete a review
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        // Implementation to delete a review
        reviewsApplicationService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }

}

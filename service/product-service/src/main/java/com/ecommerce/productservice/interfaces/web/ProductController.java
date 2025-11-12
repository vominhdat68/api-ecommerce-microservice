package com.ecommerce.productservice.interfaces.web;

import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.service.IProductApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductApplicationService productApplicationService;


    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsResponse> getProfileProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productApplicationService.getProfileProduct(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<ReviewProductResponse> getProductReviews(
            @PathVariable("id") Long productId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "sort", required = false) String sortBy,
            @RequestParam(value = "star_rating", required = false) Integer starRating
    )  {
        return ResponseEntity.ok(productApplicationService.getReviewsByProduct(
                productId,
                page,
                limit,
                sortBy,
                starRating
        ));
    }



}
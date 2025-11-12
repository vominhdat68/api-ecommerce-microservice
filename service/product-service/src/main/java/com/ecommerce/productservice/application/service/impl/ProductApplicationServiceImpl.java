package com.ecommerce.productservice.application.service.impl;

import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.application.dto.response.ReviewProductResponse;
import com.ecommerce.productservice.application.service.IProductApplicationService;
import com.ecommerce.productservice.domain.service.IProductDomainService;
import com.ecommerce.productservice.domain.service.impl.ReviewsDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements IProductApplicationService {
    private final IProductDomainService productDomainService;
    private final ReviewsDomainService reviewsDomainService;

    @Override
    public ProductDetailsResponse getProfileProduct(Long id) {
        //check Cache
        ProductDetailsResponse product = productDomainService.getProductById(id);
        return product;
    }

    @Override
    public ReviewProductResponse getReviewsByProduct(Long productId, Integer page, Integer limit, String sortBy, Integer starRating) {
        ReviewProductResponse reviewProductResponse = reviewsDomainService.getReviewsProduct(productId,page,limit,sortBy,starRating);
        return reviewProductResponse;
    }
//    private final ProductSearchService productSearchService;
}
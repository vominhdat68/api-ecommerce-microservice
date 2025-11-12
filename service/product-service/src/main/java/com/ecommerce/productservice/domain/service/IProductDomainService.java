package com.ecommerce.productservice.domain.service;

import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.domain.model.Product;
import org.springframework.data.domain.Page;

public interface IProductDomainService {
    ProductDetailsResponse getProductById(Long id);
    Page<Product> getProductsByBrand(Long id, int page, String sort);
}
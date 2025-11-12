package com.ecommerce.productservice.domain.repository;

import com.ecommerce.productservice.domain.model.Product;
import org.springframework.data.domain.Page;

public interface IProductDomainRepository {
    // Basic CRUD
    Product findByProductId(long id);

    Page<Product> getProductsByCategoryQuery(Long categoriesId, Integer page);

    Page<Product> getProductsByCategoryId(Long categoriesId, Integer page, Double minPrice, Double maxPrice, String sort);
    Page<Product> findBrandWithProducts(Long id, Integer page, String sort);
}
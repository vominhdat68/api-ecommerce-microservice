package com.ecommerce.productservice.domain.service;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.domain.model.Category;

import java.util.List;

public interface ICategoryDomainService {
    List<CategoryResponse> getAllCategory();

    CategoryResponse getCategoryDetail(Long categoriesId,Integer page);

    CategoryResponse getProductsByCategoryId(Long categoriesId, Integer page, Double minPrice, Double maxPrice, String sort);
//    CategoryResponse getCategoryById(Long id);
//    List<CategoryResponse> getCategoryTree();
//    boolean existsBySlug(String slug);
//    CategoryResponse createCategory(CategoryResponse categoryDTO);
//    CategoryResponse updateCategory(Long id, CategoryResponse categoryDTO);
//    void deleteCategory(Long id);
}
package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;

import java.util.List;

public interface ICategoryApplicationService {
    List<CategoryResponse> getCategoriesFirstLevel();
    CategoryResponse getCategoryDetail(Long categoriesId,Integer page);
    CategoryResponse getProductsByCategoryId(Long categoriesId, Integer page, Integer limit, Double minPrice, Double maxPrice, String sort);

}

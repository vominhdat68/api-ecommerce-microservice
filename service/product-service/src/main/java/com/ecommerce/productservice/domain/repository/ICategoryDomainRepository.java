package com.ecommerce.productservice.domain.repository;

import com.ecommerce.productservice.domain.model.Category;

import java.util.List;

public interface ICategoryDomainRepository {
    List<Category> getAllCategory();
    Category getCategoryDetailQuery(Long categoriesId);
//    Category save(Category category);
//    Optional<Category> findById(Long id);
//    Optional<Category> findBySlug(String slug);
//    List<Category> findAllRootCategories();
//    List<Category> findSubCategories(Long parentId);
//    List<Category> findAllActiveCategories();
//    void deleteById(Long id);
}
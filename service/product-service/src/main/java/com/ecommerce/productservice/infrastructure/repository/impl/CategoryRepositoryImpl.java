package com.ecommerce.productservice.infrastructure.repository.impl;

import com.ecommerce.productservice.application.mapper.CategoryMapper;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.repository.ICategoryDomainRepository;
import com.ecommerce.productservice.infrastructure.entity.CategoryEntity;
import com.ecommerce.productservice.infrastructure.repository.JpaCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements ICategoryDomainRepository {
    private final JpaCategoryRepository jpaCategoryRepository;

    @Override
    public List<Category> getAllCategory() {
        List<Category> categories = jpaCategoryRepository.findTopLevelCategory().stream()
                .map(CategoryMapper::toModel).toList();
        return categories;
    }

    @Override
    @Transactional(readOnly = true)
    public Category getCategoryDetailQuery(Long categoriesId) {
        CategoryEntity categories = jpaCategoryRepository.findCategoryDetailQuery(categoriesId);
        return CategoryMapper.toModel(categories);
    }


}

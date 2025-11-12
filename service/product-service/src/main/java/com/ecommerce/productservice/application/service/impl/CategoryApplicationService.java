package com.ecommerce.productservice.application.service.impl;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.application.service.ICategoryApplicationService;
import com.ecommerce.productservice.domain.service.ICategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryApplicationService implements ICategoryApplicationService {
    private final ICategoryDomainService iCategoryDomainService;

    @Override
    public List<CategoryResponse> getCategoriesFirstLevel() {
        return iCategoryDomainService.getAllCategory();
    }

    @Override
    public CategoryResponse getCategoryDetail(Long categoriesId,Integer page) {
        return iCategoryDomainService.getCategoryDetail(categoriesId,page);
    }

    @Override
    public CategoryResponse getProductsByCategoryId(Long categoriesId, Integer page, Integer limit, Double minPrice, Double maxPrice, String sort) {
        return iCategoryDomainService.getProductsByCategoryId(categoriesId,page,minPrice,maxPrice,sort);
    }


}
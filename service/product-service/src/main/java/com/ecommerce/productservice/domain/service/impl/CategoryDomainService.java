package com.ecommerce.productservice.domain.service.impl;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.application.mapper.CategoryMapper;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.repository.ICategoryDomainRepository;
import com.ecommerce.productservice.domain.repository.IProductDomainRepository;
import com.ecommerce.productservice.domain.service.ICategoryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CategoryDomainService implements ICategoryDomainService {
    private final ICategoryDomainRepository iCategoryDomainRepository;
    private final IProductDomainRepository iProductDomainRepository;


    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = iCategoryDomainRepository.getAllCategory();

        return categories.stream().map(CategoryMapper::toResponse).toList();
    }

    @Override
    public CategoryResponse getCategoryDetail(Long categoriesId,Integer page) {
// các truy vấn song song
        CompletableFuture<Category> categoryFuture =
                CompletableFuture.supplyAsync(() -> iCategoryDomainRepository.getCategoryDetailQuery(categoriesId));
        CompletableFuture<Page<Product>> productsFuture =
                CompletableFuture.supplyAsync(() -> iProductDomainRepository.getProductsByCategoryQuery(categoriesId,page));
// Đợi cả 3 hoàn tất
        CompletableFuture.allOf(categoryFuture, productsFuture).join();
        return CategoryMapper.toResponseDetails(categoryFuture.join(),productsFuture.join());
    }

    @Override
    public CategoryResponse getProductsByCategoryId(Long categoriesId, Integer page, Double minPrice, Double maxPrice, String sort) {
        Page<Product> products = iProductDomainRepository.getProductsByCategoryId(categoriesId,page,minPrice,maxPrice,sort);
        return CategoryMapper.toResponseDetails(null,products);
    }
}

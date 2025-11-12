package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.infrastructure.entity.CategoryEntity;
import com.ecommerce.productservice.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static Category toModel(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        category.setSlug(entity.getSlug());
        category.setLevel(entity.getLevel());
        category.setImageUrl(entity.getImageUrl());
        category.setActive(entity.isActive());

        List<Category> childModels = new ArrayList<>();
        for (CategoryEntity childEntity : entity.getChildren()) {
            Category c = new Category();
            c.setId(childEntity.getId());
            c.setName(childEntity.getName());
            childModels.add(c);
        }
        category.setChildren(childModels);


        return category;
    }
    public static CategoryResponse toResponse(Category model) {
        CategoryResponse category = new CategoryResponse();
        category.setId(model.getId());
        category.setName(model.getName());
        category.setSlug(model.getSlug());
        category.setLevel(model.getLevel());
        category.setImageUrl(model.getImageUrl());
        category.setActive(model.isActive());

        List<CategoryResponse> childModels = new ArrayList<>();
        for (Category child : model.getChildren()) {
            CategoryResponse c = new CategoryResponse();
            c.setId(child.getId());
            c.setName(child.getName());
            childModels.add(c);
        }
        category.setSub_categories(childModels);


        return category;
    }

    public static CategoryResponse toResponseDetails(Category model, Page<Product> products) {
        CategoryResponse categoryResponse;
    if(model != null){
            categoryResponse = toResponse(model);
        }
    else { categoryResponse = new CategoryResponse();}

                List<ProductDetailsResponse> productDetails = products.getContent().stream().map(
                product -> {
                    ProductDetailsResponse detailsResponse = new ProductDetailsResponse();
                    detailsResponse.setId(product.getId());
                    detailsResponse.setName(product.getName());
                    detailsResponse.setPrice(product.getPrice());
                    detailsResponse.setDiscountPrice(product.getDiscountPrice());
                    detailsResponse.setHas_discount(product.hasDiscount());
                    detailsResponse.setIn_stock(product.isInStock());
                    detailsResponse.setStockQuantity(product.getStockQuantity());
                    return detailsResponse;
                }
        ).toList();
        categoryResponse.setProducts(new PageImpl<>(
                productDetails,
                products.getPageable(),
                products.getTotalElements()));

        return categoryResponse;
    }


}

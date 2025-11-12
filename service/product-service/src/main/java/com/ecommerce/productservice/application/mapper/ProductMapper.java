package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.domain.model.*;
import com.ecommerce.productservice.infrastructure.entity.ProductAttributeEntity;
import com.ecommerce.productservice.infrastructure.entity.ProductEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductProjection;

import java.util.*;
import java.util.stream.Collectors;

public class ProductMapper {
    public static Product toModel(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setDiscountPrice(entity.getDiscountPrice());
        product.setStockQuantity(entity.getStockQuantity());
        product.setSku(entity.getSku());
        product.setCategory(CategoryMapper.toModel(entity.getCategory()));
        product.setBrand(BrandMapper.toModel(entity.getBrand()));

        Set<ProductAttribute> attributes = new HashSet<>();
        for (ProductAttributeEntity attribute : entity.getAttributes()) {
            attributes.add(ProductAttributeMapper.toModel(attribute));
        }

        product.setAttributes(attributes);

        product.setFeatured(entity.isFeatured());
        product.setActive(entity.isActive());
        return product;
    }

    public static ProductDetailsResponse toProductDetails(Product model) {
        ProductDetailsResponse response = new ProductDetailsResponse();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setSlug(model.getName());
        response.setDescription(model.getDescription());
        response.setPrice(model.getDiscountPrice());
        response.setDiscountPrice(model.getDiscountPrice());
        response.setCurrentPrice(model.getCurrentPrice());
        response.setStockQuantity(model.getStockQuantity());
        response.setSku(model.getSku());

        response.setAverageRating(null);
        response.setReviewCount(null);


        CategoryResponse categoryResponse = new CategoryResponse();
        Category category  = model.getCategory();
//        categoryResponse.setParentId(category.getParent().getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSlug(category.getSlug());
        categoryResponse.setImageUrl(category.getImageUrl());
        categoryResponse.setSub_categories(category.getChildren().stream()
                .map(c-> new CategoryResponse(c.getId(),c.getName())).toList()

        );
                response.setCategory(categoryResponse);
                response.setBrand(new BrandResponse(model.getBrand().getId(),model.getBrand().getName()));

        Map<String, List<String>> attributes = model.getAttributes().stream()
                .collect(Collectors.groupingBy(
                attr -> attr.getAttribute().getName().toLowerCase(),
                Collectors.mapping(
                        ProductAttribute::getValue,
                        Collectors.toList()
                )
        ));
                response.setAttributes(attributes);
                response.setIs_featured(model.isFeatured());
                response.setIs_active(model.isActive());
                response.setIn_stock(model.isInStock());
                response.setHas_discount(model.hasDiscount());

        return response;
    }

    public static Product toModel(ProductProjection entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setPrice(entity.getPrice());
        product.setDiscountPrice(entity.getDiscountPrice());
        product.setStockQuantity(entity.getStockQuantity());
        return product;
    }


}
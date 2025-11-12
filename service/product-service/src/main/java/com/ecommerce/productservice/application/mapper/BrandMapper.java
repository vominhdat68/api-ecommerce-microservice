package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.infrastructure.entity.BrandEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.BrandProjection;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class BrandMapper {


    public static Brand toModel(BrandEntity brand) {
        Brand  model = new Brand();
        model.setId(brand.getId());
        model.setName(brand.getName());
    return model;
    }

    public static Brand toModel(BrandProjection brand) {
        Brand  model = new Brand();
        model.setId(brand.getId());
        model.setName(brand.getName());
        return model;
    }

    public static BrandResponse toResponse(BrandProjection entity) {
        BrandResponse  response = new BrandResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSlug(entity.getSlug());
        response.setLogoUrl(entity.getLogoUrl());
        response.setFeatured(entity.getIsFeatured());
        response.setProductCount(entity.getProductCount());

        return response;
    }

    public static BrandResponse toResponse(Brand entity) {
        BrandResponse  response = new BrandResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSlug(entity.getSlug());
        response.setLogoUrl(entity.getLogoUrl());

        List<ProductDetailsResponse> brandResponses = entity.getProducts().stream()
                .map(p -> {
                    ProductDetailsResponse pr = new ProductDetailsResponse();
                    pr.setId(p.getId());
                    pr.setName(p.getName());
                    pr.setPrice(p.getPrice());
                    pr.setDiscountPrice(p.getDiscountPrice());
                    pr.setIn_stock(p.isInStock());
                    pr.setStockQuantity(p.getStockQuantity());
                    return pr;
                }).toList();
        response.setBrandProductResponses(new PageImpl<>(
                brandResponses,
                entity.getProducts().getPageable(),
                entity.getProducts().getTotalElements()));
        return response;
    }

}

package com.ecommerce.productservice.infrastructure.repository.impl;

import com.ecommerce.productservice.application.mapper.ProductMapper;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.repository.IProductDomainRepository;
import com.ecommerce.productservice.infrastructure.entity.ProductEntity;
import com.ecommerce.productservice.infrastructure.repository.JpaProductRepository;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.ResourceNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements IProductDomainRepository {
    private final JpaProductRepository jpaProductRepository;


    @Override
    public Product findByProductId(long id) {
        ProductEntity productEntity = jpaProductRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return ProductMapper.toModel(productEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> getProductsByCategoryQuery(Long categoriesId,Integer page) {
        final int limit = 20;
        PageRequest pageRequest = PageRequest.of(page != null ? page : 0,limit);
        Page<ProductProjection> productsP = jpaProductRepository.findByProductsCategoryId(categoriesId,pageRequest);
        List<Product> products = productsP.getContent().stream().map(ProductMapper::toModel).toList();
        return new PageImpl<>(
                products,
                productsP.getPageable(),
                productsP.getTotalElements());
    }

    @Override
    public Page<Product> getProductsByCategoryId(Long categoriesId, Integer page, Double minPrice, Double maxPrice, String sortBy) {
        final int limit = 20;
        PageRequest pageRequest = PageRequest.of(
                page != null ? page : 0,
                limit,
                sortBy != null ? Sort.by(sortBy).descending() : Sort.unsorted()
        );

        if (minPrice == null) minPrice = 0.0;
        if (maxPrice == null) maxPrice = Double.MAX_VALUE;
        Page<ProductProjection> productsP = jpaProductRepository.findByPriceBetweenProducts(categoriesId,minPrice,maxPrice,pageRequest);
        List<Product> products = productsP.getContent().stream().map(ProductMapper::toModel).toList();
        return new PageImpl<>(
                products,
                productsP.getPageable(),
                productsP.getTotalElements());
    }

    @Override
    public Page<Product> findBrandWithProducts(Long id, Integer page, String sortBy) {
        final int limit = 20;
        PageRequest pageRequest = PageRequest.of(
                page != null ? page : 0,limit,
                sortBy != null ? Sort.by(sortBy).descending() : Sort.unsorted()
        );
        Page<ProductProjection> pProducts = jpaProductRepository.findBrandWithProducts(id,pageRequest);

        List<Product> products = pProducts.getContent().stream()
               .map(ProductMapper::toModel).toList();

       return new PageImpl<>(
               products,
               pProducts.getPageable(),
               pProducts.getTotalElements());
    }
}
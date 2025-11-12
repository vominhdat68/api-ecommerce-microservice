package com.ecommerce.productservice.domain.service.impl;

import com.ecommerce.productservice.application.dto.response.ProductDetailsResponse;
import com.ecommerce.productservice.application.mapper.ProductMapper;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.repository.IProductDomainRepository;
import com.ecommerce.productservice.domain.service.IProductDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDomainService implements IProductDomainService {
    private final IProductDomainRepository productDomainRepository;

    @Override
    public ProductDetailsResponse getProductById(Long id)   {
        Product product = productDomainRepository.findByProductId(id);
        return ProductMapper.toProductDetails(product);

    }

    @Override
    public Page<Product> getProductsByBrand(Long id, int page, String sort) {
        Page<Product> products = productDomainRepository.findBrandWithProducts(id,page,sort);
        return products;
    }


}

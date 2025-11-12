package com.ecommerce.productservice.application.service.impl;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.application.mapper.BrandMapper;
import com.ecommerce.productservice.application.service.IBrandApplicationService;
import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.domain.model.Product;
import com.ecommerce.productservice.domain.service.IBrandDomainService;
import com.ecommerce.productservice.domain.service.IProductDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandApplicationService implements IBrandApplicationService {
    private final IBrandDomainService iBrandDomainService;
    private final IProductDomainService iProductDomainService;

    @Override
    public List<BrandResponse> getBrandsByName(String brandName) {
        return iBrandDomainService.findByNameContainingIgnoreCase(brandName);
    }


    @Override
    public BrandResponse getProductsByBrand(Long id, int page, int limit, String sort) {
        Brand brand = iBrandDomainService.getBrandById(id);
        Page<Product> productPage = iProductDomainService.getProductsByBrand(id,page,sort);
        brand.setProducts(productPage);
        return BrandMapper.toResponse(brand);
    }
}

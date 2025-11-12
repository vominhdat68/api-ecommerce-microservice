package com.ecommerce.productservice.infrastructure.repository.impl;

import com.ecommerce.productservice.application.mapper.BrandMapper;
import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.domain.repository.IBrandRepository;
import com.ecommerce.productservice.infrastructure.repository.JpaBrandRepository;
import com.ecommerce.productservice.infrastructure.repository.projection.BrandProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandRepositoryImpl implements IBrandRepository {
    private final JpaBrandRepository jpaBrandRepository;


    @Override
    public List<BrandProjection> findByNameContainingIgnoreCase(String name) {
            List<BrandProjection>  brandEntities = jpaBrandRepository.findAllBrandsWithProductCount();
            return brandEntities;
    }

    @Override
    public Brand getBrandById(Long id) {
        BrandProjection brand = jpaBrandRepository.getBrandById(id);
        return BrandMapper.toModel(brand);
    }


}

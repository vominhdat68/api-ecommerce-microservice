package com.ecommerce.productservice.domain.service.impl;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.application.mapper.BrandMapper;
import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.domain.repository.IBrandRepository;
import com.ecommerce.productservice.domain.service.IBrandDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandDomainService implements IBrandDomainService {
    private final IBrandRepository iBrandRepository;

    @Override
    public List<BrandResponse> findByNameContainingIgnoreCase(String brandName) {
        List<BrandResponse> brandResponses = iBrandRepository.findByNameContainingIgnoreCase(brandName).stream()
                .map(BrandMapper::toResponse).toList();

        return brandResponses;
    }

    @Override
    public Brand getBrandById(Long id) {
        return iBrandRepository.getBrandById(id);
    }


}

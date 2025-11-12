package com.ecommerce.cartservice.infrastructure.repository.impl;

import com.ecommerce.cartservice.application.mapper.ShippingMethodMapper;
import com.ecommerce.cartservice.domain.model.ShippingMethod;
import com.ecommerce.cartservice.domain.repository.IShippingMethodRepository;
import com.ecommerce.cartservice.infrastructure.repository.JpaShippingMethodRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ShippingMethodRepositoryImpl implements IShippingMethodRepository {

    private final JpaShippingMethodRepository jpaRepo;

    public ShippingMethodRepositoryImpl(JpaShippingMethodRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public List<ShippingMethod> findAll() {
        return jpaRepo.findAll().stream()
                .map(ShippingMethodMapper::toResponseList)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ShippingMethod> findByCode(String code) {
        return jpaRepo.findByCode(code).map(ShippingMethodMapper::toResponseList);
    }
}

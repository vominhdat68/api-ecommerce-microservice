package com.ecommerce.cartservice.application.service.impl;

import com.ecommerce.cartservice.domain.model.ShippingMethod;
import com.ecommerce.cartservice.domain.repository.IShippingMethodRepository;
import com.ecommerce.cartservice.domain.service.IShippingDomainService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements IShippingDomainService {

    private final IShippingMethodRepository shippingMethodRepository;

    public ShippingServiceImpl(IShippingMethodRepository shippingMethodRepository) {
        this.shippingMethodRepository = shippingMethodRepository;
    }

    @Override
    public List<ShippingMethod> getAvailableShippingMethods() {
        return shippingMethodRepository.findAll();
    }

    @Override
    public Optional<ShippingMethod> findByCode(String code) {
        return Optional.empty();
    }
}

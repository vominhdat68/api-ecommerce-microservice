package com.ecommerce.cartservice.domain.repository;

import com.ecommerce.cartservice.domain.model.ShippingMethod;

import java.util.List;
import java.util.Optional;

public interface IShippingMethodRepository {

    List<ShippingMethod> findAll();

    Optional<ShippingMethod> findByCode(String code);
}


package com.ecommerce.cartservice.infrastructure.repository;

import com.ecommerce.cartservice.infrastructure.entity.ShippingMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaShippingMethodRepository extends JpaRepository<ShippingMethodEntity, Long> {
    Optional<ShippingMethodEntity> findByCode(String code);
}
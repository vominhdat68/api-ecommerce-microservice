package com.ecommerce.cartservice.infrastructure.repository;

import com.ecommerce.cartservice.infrastructure.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCouponRepository extends JpaRepository<CouponEntity, Long> {
    Optional<CouponEntity> findByCode(String code);
}


package com.ecommerce.cartservice.domain.repository;

import com.ecommerce.cartservice.domain.model.Coupon;

import java.util.Optional;

public interface ICouponRepository {

    Optional<Coupon> findByCode(String code);
}


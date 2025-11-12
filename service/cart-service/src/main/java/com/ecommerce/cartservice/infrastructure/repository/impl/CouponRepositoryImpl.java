package com.ecommerce.cartservice.infrastructure.repository.impl;

import com.ecommerce.cartservice.application.mapper.CouponMapper;
import com.ecommerce.cartservice.domain.model.Coupon;
import com.ecommerce.cartservice.domain.repository.ICouponRepository;
import com.ecommerce.cartservice.infrastructure.repository.JpaCouponRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponRepositoryImpl implements ICouponRepository {

    private final JpaCouponRepository jpaCouponRepository;

    public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository) {
        this.jpaCouponRepository = jpaCouponRepository;
    }

    @Override
    public Optional<Coupon> findByCode(String code) {
        return jpaCouponRepository.findByCode(code)
                .map(CouponMapper::toCoupon);
    }
}

package com.ecommerce.cartservice.application.mapper;

import com.ecommerce.cartservice.domain.model.Coupon;
import com.ecommerce.cartservice.infrastructure.entity.CouponEntity;
import org.springframework.stereotype.Component;

@Component
public class CouponMapper {


    public static Coupon toCoupon(CouponEntity couponEntity) {
        return Coupon.builder()
                .code(couponEntity.getCode())
                .discountPercent(couponEntity.getDiscountAmount())
                .build();
    }
}
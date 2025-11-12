package com.ecommerce.cartservice.domain.service;

import com.ecommerce.cartservice.domain.model.Coupon;

import java.util.Optional;

public interface ICouponDomainService {

    /**
     * Tìm kiếm mã giảm giá hợp lệ theo mã code
     * @param code mã giảm giá do người dùng nhập
     * @return Coupon hợp lệ nếu có
     */
    Optional<Coupon> findValidCoupon(String code);

    /**
     * Kiểm tra xem coupon có hợp lệ với giỏ hàng không (ví dụ: tối thiểu đơn hàng, sản phẩm hợp lệ, thời gian,...)
     * @param coupon Coupon cần kiểm tra
     * @return true nếu hợp lệ, ngược lại false
     */
    boolean isApplicable(Coupon coupon);
}


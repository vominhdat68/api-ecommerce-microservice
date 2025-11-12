package com.ecommerce.cartservice.domain.service;

import com.ecommerce.cartservice.domain.model.ShippingMethod;

import java.util.List;
import java.util.Optional;

public interface IShippingDomainService {

    /**
     * Trả về tất cả các phương thức giao hàng khả dụng
     */
    List<ShippingMethod> getAvailableShippingMethods();

    /**
     * Tìm kiếm phương thức giao hàng theo mã
     * @param code mã phương thức giao hàng
     * @return phương thức tương ứng nếu có
     */
    Optional<ShippingMethod> findByCode(String code);
}


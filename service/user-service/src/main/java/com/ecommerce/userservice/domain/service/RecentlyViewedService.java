package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.RecentlyViewedProduct;

import java.util.List;

public interface RecentlyViewedService {
    List<RecentlyViewedProduct> getRecentlyViewedProducts(Long userId);
    void saveViewedProduct(RecentlyViewedProduct product);
}



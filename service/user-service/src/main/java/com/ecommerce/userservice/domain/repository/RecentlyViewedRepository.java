package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.RecentlyViewedProduct;

import java.util.List;

public interface RecentlyViewedRepository {
    List<RecentlyViewedProduct> findTop10ByUserIdOrderByViewedAtDesc(Long userId);
    RecentlyViewedProduct save(RecentlyViewedProduct viewedProduct);
    void deleteOldestIfExceedsLimit(Long userId, int maxLimit);
}

package com.ecommerce.userservice.infrastructure.repository.impl;

import com.ecommerce.userservice.domain.model.RecentlyViewedProduct;
import com.ecommerce.userservice.domain.repository.RecentlyViewedRepository;
import com.ecommerce.userservice.infrastructure.repository.JpaRecentlyViewedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecentlyViewedRepositoryImpl  implements RecentlyViewedRepository {
    private final JpaRecentlyViewedRepository jpa;

    @Override
    public List<RecentlyViewedProduct> findTop10ByUserIdOrderByViewedAtDesc(Long userId) {
        return List.of();
    }

    @Override
    public RecentlyViewedProduct save(RecentlyViewedProduct viewedProduct) {
        return null;
    }

    @Override
    public void deleteOldestIfExceedsLimit(Long userId, int maxLimit) {

    }

    // Ánh xạ các phương thức tương tự như trên
}


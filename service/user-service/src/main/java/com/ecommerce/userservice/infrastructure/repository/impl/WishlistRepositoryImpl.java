package com.ecommerce.userservice.infrastructure.repository.impl;

import com.ecommerce.userservice.domain.model.WishlistItem;
import com.ecommerce.userservice.domain.repository.WishlistRepository;
import com.ecommerce.userservice.infrastructure.repository.JpaWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WishlistRepositoryImpl implements WishlistRepository {
    private final JpaWishlistRepository jpa;

    @Override
    public List<WishlistItem> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Optional<WishlistItem> findByUserIdAndProductId(Long userId, Long productId) {
        return Optional.empty();
    }

    @Override
    public WishlistItem save(WishlistItem item) {
        return null;
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {

    }

    // Ánh xạ các phương thức tương tự như trên
}

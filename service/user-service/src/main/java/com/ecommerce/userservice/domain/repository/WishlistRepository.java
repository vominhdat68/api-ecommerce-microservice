package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.WishlistItem;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository {
    List<WishlistItem> findByUserId(Long userId);
    Optional<WishlistItem> findByUserIdAndProductId(Long userId, Long productId);
    WishlistItem save(WishlistItem item);
    void deleteByUserIdAndProductId(Long userId, Long productId);
}


package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.WishlistItem;

import java.util.List;

public interface WishlistItemService {
    List<WishlistItem> getWishlist(Long userId);
    void addToWishlist(Long userId, Long productId);
    void removeFromWishlist(Long userId, Long productId);
}



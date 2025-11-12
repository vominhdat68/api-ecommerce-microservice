package com.ecommerce.cartservice.domain.repository;

import com.ecommerce.cartservice.domain.model.Cart;

import java.util.Optional;

public interface ICartCacheRepository {
    Optional<Cart> getCart(Long userId);
    void saveCart(Long userId, Cart cart);
    void updateItemQuantity(Long userId, Long itemId, int quantity);

    void deleteCart(Long userId);

}

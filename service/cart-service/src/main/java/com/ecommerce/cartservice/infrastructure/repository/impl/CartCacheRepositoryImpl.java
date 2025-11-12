package com.ecommerce.cartservice.infrastructure.repository.impl;

import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.repository.ICartCacheRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class CartCacheRepositoryImpl implements ICartCacheRepository {
    private static final String ANONYMOUS_USER  = "anonymous_cart:";
    private static final String LOGIN_USER  = "user_cart:";
    private static final long TTL = 30; // minutes

    private final RedisTemplate<String, Cart> redisTemplate;

    public CartCacheRepositoryImpl(RedisTemplate<String, Cart> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private String getKey(Long userId) {
        return userId == -1? ANONYMOUS_USER + userId : LOGIN_USER + userId ;
    }

    @Override
    public Optional<Cart> getCart(Long userId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(getKey(userId)));
    }

    @Override
    public void saveCart(Long userId, Cart cart) {
        redisTemplate.opsForValue().set(getKey(userId), cart, TTL, TimeUnit.MINUTES);
    }

    @Override
    public void updateItemQuantity(Long userId, Long itemId, int quantity) {
        String key = getKey(userId);
        Cart cart = redisTemplate.opsForValue().get(key);

        if (cart == null) return;
        cart.updateItemQuantity(itemId, quantity);
        cart.calculateEstimatedCost();
        redisTemplate.opsForValue().set(key, cart, TTL, TimeUnit.MINUTES);
    }

    @Override
    public void deleteCart(Long userId) {
        redisTemplate.delete(getKey(userId));
    }


}

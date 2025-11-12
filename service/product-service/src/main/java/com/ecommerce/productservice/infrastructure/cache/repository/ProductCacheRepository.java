package com.ecommerce.productservice.infrastructure.cache.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

//@Repository
@RequiredArgsConstructor
public class ProductCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;







}

package com.ecommerce.user.cache.Redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public class RedisCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void putInCache(String key, Object value, long timeout) {
        redisTemplate.opsForValue().set(key,value,timeout);
    }

    /**
     * Thêm phần tử vào Redis Set (đảm bảo không trùng lặp)
     * @param setKey Key của Set trong Redis
     * @param value Giá trị cần thêm
     */
    public void addToSet(String setKey, Object value) {
         redisTemplate.opsForSet().add(setKey, value);
    }
    public void addToSet(String setKey, Object value, long expiration) {
        redisTemplate.opsForValue().set(setKey, value , expiration);
    }

//>>>>> List
    public Object popFromListHead(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * Thêm phần tử vào cuối Redis List (cấu trúc queue)
     * @param listKey Key của List trong Redis
     * @param value Giá trị cần thêm
     */
    public void pushToList(String listKey, Object value) {
         redisTemplate.opsForList().rightPush(listKey, value);
    }


}

package com.ecommerce.auth.cache;

import com.ecommerce.auth.cache.metadata.RefreshTokenMetadata;
import com.ecommerce.shared_libs.cache.Redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AuthCache {
    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheRefreshToken(String refreshToken, RefreshTokenMetadata metadata,long  expirationSeconds) {
        String refreshKey = RedisKeys.REFRESH_TOKEN_PREFIX + refreshToken;
        Map<String, String> tokenData = new HashMap<>();
        tokenData.put("username", metadata.getUsername());
        tokenData.put("jti", metadata.getJti());
        redisTemplate.opsForHash().putAll(refreshKey, tokenData);
        redisTemplate.expire(refreshKey,expirationSeconds, TimeUnit.SECONDS);
    }
    public boolean existKeyRefresh(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.REFRESH_TOKEN_PREFIX+key));
    }
    public void deleteRefresh(String key){
        redisTemplate.delete(RedisKeys.REFRESH_TOKEN_PREFIX+key);
    }
    public String extractUsernameFromRefresh(String key){
        return (String) redisTemplate.opsForHash().get(RedisKeys.REFRESH_TOKEN_PREFIX+key, "username");
    }
    public String extractJtiFromRefresh(String key){
        return (String) redisTemplate.opsForHash().get(RedisKeys.REFRESH_TOKEN_PREFIX+key,"jti");
    }
    public boolean existKeyJti(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.JTI_PREFIX+key));
    }
    public void cacheJtiRevoke(String key, boolean revoke, long expiration){
        redisTemplate.opsForValue().set(RedisKeys.JTI_PREFIX+key,revoke,expiration,TimeUnit.SECONDS);
    }
    public void deleteKeysJti(String key) {
        redisTemplate.delete(RedisKeys.JTI_PREFIX+key);
    }

}

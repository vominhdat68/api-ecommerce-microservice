package com.ecommerce.shared_libs.cache.Redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPCache {
    private final RedisTemplate<String, Object> redisTemplate;

    public void cacheRegisteredEmail(String email) {
        redisTemplate.opsForValue().set(RedisKeys.REGISTER_EMAIL_KEY+email,"",RedisKeys.REGISTER_EMAIL_KEY_TTL);
    }
    public boolean isEmailRegistered(String email) {
        return redisTemplate.opsForValue().get(RedisKeys.REGISTER_EMAIL_KEY+email) != null;
    }
    public void removeEmailCache(String email) {
        redisTemplate.delete(RedisKeys.REGISTER_EMAIL_KEY+email);
    }
    public void cacheOtp(String email, String otp) {
        redisTemplate.opsForValue().set(RedisKeys.VERIFY_OTP_EMAIL_KEY+email, otp, RedisKeys.VERIFY_OTP_EMAIL_KEY_TTL);
    }
    public boolean validateOtp(String email, String otp) {
        String cachedOtp = (String) redisTemplate.opsForValue().get(RedisKeys.VERIFY_OTP_EMAIL_KEY+email);
        return otp.equals(cachedOtp);
    }
    public void clearOtp(String email) {
        redisTemplate.delete(RedisKeys.VERIFY_OTP_EMAIL_KEY+email);
    }
}

package com.ecommerce.auth.cache;

import com.ecommerce.auth.cache.metadata.RefreshTokenMetadata;
import com.ecommerce.auth.entity.User;
import com.ecommerce.user.cache.Redis.RedisKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class AuthCache {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final int MAX_FAILED_ATTEMPTS = 5;

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
    public void putForgotTokenVerify(String token,String email) {
        redisTemplate.opsForValue().set(RedisKeys.FORGOT_PASSWORD_TOKEN_KEY+token,email,RedisKeys.FORGOT_PASSWORD_TOKEN_TTL);
    }
    public String getForgotTokenVerify(String token) {
        return (String) redisTemplate.opsForValue().get(RedisKeys.FORGOT_PASSWORD_TOKEN_KEY+token);
    }







/**
 *  lock tạm thời (5s) cho tài khoản đăng được người dùng đăng ký nhưng chưa hoàn thành
 *@setIfAbsent(): Ghi giá trị "locked" vào Redis nếu key chưa tồn tại
 *@LOCK_TIMEOUT (5s): Tự động xóa lock nếu quá thời gian.
 * */
    public boolean acquireLock(String key) {
        return Boolean.TRUE.equals(
                redisTemplate.opsForValue()
                        .setIfAbsent(RedisKeys.REGISTRATION_LOCK_PREFIX + key, "locked", RedisKeys.REGISTER_LOCK_TIMEOUT_TTL)
        );
    }

    /**
     *  Giải phóng lock
     * Xóa lock sau khi xử lý xong
     * Luôn đặt trong khối finally để tránh treo lock.
     * */
    public void releaseLock(String key) {
        redisTemplate.delete(RedisKeys.REGISTRATION_LOCK_PREFIX + key);
    }

    /**
     *  Kiểm tra trùng request
     *  Phát hiện nếu email đang trong quá trình đăng ký.
     * */
    public boolean isAlreadyProcessing(String email) {
        return redisTemplate.hasKey(RedisKeys.OTP_PREFIX + email) ||
                redisTemplate.hasKey(RedisKeys.PENDING_USER_PREFIX + email);
    }

    /**
     *  Lưu OTP và user tạm
     *  Lưu trữ tạm thời OTP và thông tin user chờ xác thực.
     * @OTP Cache -> TTL 5 phút (OTP_EXPIRE)
     * @User Cache -> TTL 6 phút (dài hơn OTP 1 phút)
     *
     * */
    public void cacheRegistrationData(String email, String otp, User user) {
        redisTemplate.opsForValue().set(RedisKeys.OTP_PREFIX + email, otp, RedisKeys.OTP_EXPIRE_TTL);
        redisTemplate.opsForValue().set(
                RedisKeys.PENDING_USER_PREFIX + email,
                user,
                RedisKeys.OTP_EXPIRE_TTL.plusMinutes(1)
        );
    }

    public void rollbackRegistrationCache(String email) {
            redisTemplate.delete(RedisKeys.OTP_PREFIX + email);
            redisTemplate.delete(RedisKeys.PENDING_USER_PREFIX + email);
    }

    public String getAndDeleteOtp(String email) {
        return (String) redisTemplate.opsForValue()
                .getAndDelete(RedisKeys.OTP_PREFIX + email);
    }

    public User getAndDeletePendingUser(String email) {
        return (User) redisTemplate.opsForValue()
                .getAndDelete(RedisKeys.PENDING_USER_PREFIX + email);
    }

    public boolean isValidOtp(String cachedOtp, String inputOtp) {
        return cachedOtp != null && cachedOtp.equals(inputOtp);
    }

    //OTP
    public int getCurrentRetryCount(String email) {
        String key = RedisKeys.OTP_RETRY_COUNT_PREFIX + email;
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        return count != null ? count : 0;
    }
    public void saveOtpAndUpdateRetryCount(String email, String otp) {
        redisTemplate.execute(
                new SessionCallback<>() {// Đánh dấu bắt đầu một transaction
                    @Override
                    public Object execute(RedisOperations operations) throws DataAccessException {
                        operations.multi();

                    // Lưu OTP mới
                        operations.opsForValue().set(
                                RedisKeys.OTP_PREFIX + email,
                                otp,
                                RedisKeys.OTP_EXPIRE_TTL
                        );
                    // Tăng số lần gửi
                        String retryKey = RedisKeys.OTP_RETRY_COUNT_PREFIX + email;
                        operations.opsForValue().increment(retryKey);
                        operations.expire(retryKey, RedisKeys.RETRY_WINDOW_TTL);

                        return operations.exec();// Thực thi tất cả lệnh cùng lúc
                    }
                }

        );



    }

    public boolean isRateLimited(String email) {
        // Triển khai Redis-based rate limiting
        return false; // Mock implementation
    }
    //LOGIN

    /** Ghi lại số lần đăng nhập sai phục vụ cho việc chặn đăng nhập của khoản tạm thời theo ip thiết bị
     * @param sessionId ID phiên đăng nhập (thường là token hoặc session ID)
     */
    public void recordFailedLogin(String sessionId, String clientIp) {
        // Tăng giá trị và lấy giá trị mới
        Long attempts = redisTemplate.opsForValue().increment(RedisKeys.FAILED_LOGIN_PREFIX + sessionId);

        // Nếu là lần đầu (attempts == 1), set expiration
        if (attempts != null && attempts == 1) {
            redisTemplate.expire(RedisKeys.FAILED_LOGIN_PREFIX + sessionId, RedisKeys.FAILED_LOGIN_PREFIX_TTL);
        }

        // Nếu vượt quá số lần cho phép, block tài khoản tạm thời
        if (attempts != null && attempts >= MAX_FAILED_ATTEMPTS) {
            redisTemplate.opsForValue().set(RedisKeys.LOGIN_LOCKOUT_ACCOUNT + sessionId +":"+ clientIp, "locked");
            redisTemplate.expire(RedisKeys.LOGIN_LOCKOUT_ACCOUNT + sessionId +":"+ clientIp, RedisKeys.LOGIN_LOCKOUT_ACCOUNT_TTL);

            // Clean failed attempts count
            redisTemplate.delete(RedisKeys.FAILED_LOGIN_PREFIX + sessionId);
        }

    }

    /** Kiểm tra tài khoản có bị chặn đăng nhập tạm thời không
     * @param sessionId ID phiên đăng nhập (thường là token hoặc session ID)
     */
    public boolean isAccountLocked(String sessionId, String clientIp) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.LOGIN_LOCKOUT_ACCOUNT + sessionId +":"+ clientIp));
    }

    /**
     * Lưu thông tin đăng nhập vào cache
     * @param sessionId ID phiên đăng nhập (thường là token hoặc session ID)
     * @param user Thông tin người dùng cần cache
     */
    public void cacheLoginSession(String sessionId, User user) {
        // cap nhat lai user
        User cachedUser = new User(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled()
        );
        cachedUser.setAuthorities(user.getAuthorities());

        redisTemplate.opsForValue().set(RedisKeys.LOGIN_CACHE_PREFIX + sessionId, user, RedisKeys.LOGIN_CACHE_TTL);
    }

    /**
     * Lấy thông tin đăng nhập từ cache
     * @param sessionId ID phiên đăng nhập
     * @return Thông tin người dùng đã cache hoặc null nếu không tồn tại/hết hạn
     */
    public User getCachedLoginSession(String sessionId) {
        try {
            return (User) redisTemplate.opsForValue().get(RedisKeys.LOGIN_CACHE_PREFIX + sessionId);
        }catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        }

    /**
     * Kiểm tra session có tồn tại trong cache không
     * @param sessionId ID phiên đăng nhập
     * @return true nếu tồn tại, false nếu không
     */
    public boolean isSessionExist(String sessionId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(RedisKeys.LOGIN_CACHE_PREFIX + sessionId));
    }

    /**
     * Xóa thông tin đăng nhập khỏi cache (khi logout)
     * @param sessionId ID phiên đăng nhập
     */
    public void removeLoginSession(String sessionId) {
        redisTemplate.delete(RedisKeys.LOGIN_CACHE_PREFIX + sessionId);
    }
}

package com.ecommerce.user.cache.Redis;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RedisKeys {
    // register
    public static final String REGISTRATION_LOCK_PREFIX  ="reg_lock:";
    public static final String OTP_PREFIX  ="otp:reg:";
    public static final String PENDING_USER_PREFIX = "pending_user:";
    // resend otp
    public static final String OTP_RETRY_COUNT_PREFIX = "otp_retry:";
    public static final int MAX_RETRY = 3;
    public static final Duration COOLDOWN_PERIOD = Duration.ofHours(2);
    //login
    public static final String LOGIN_CACHE_PREFIX = "login:session:";
    public static final String LOGIN_LOCKOUT_ACCOUNT = "login:locked:";
    public static final String FAILED_LOGIN_PREFIX = "login:fail:";

    public static final String FORGOT_PASSWORD_TOKEN_KEY ="token:forgot:";
    public static final String REFRESH_TOKEN_PREFIX = "auth:session:";
    public static final String JTI_PREFIX = "auth:jti:";
    public static final String REFRESH_USER_INDEX = "auth:refresh:index:"; // key này dùng cho Set -> phục vụ việc tìm kiếm nhanh
    public static final String REFRESH_USER_ORDER = "auth:refresh:index:%s:order";// key này dùng cho List -> lưu theo thứ tự thiết bị đăng nhập

    // TTL settings for Redis keys
    public static final long USER_CACHE_TTL = TimeUnit.HOURS.toSeconds(24);
    public static final long VERIFY_OTP_EMAIL_KEY_TTL = TimeUnit.MINUTES.toSeconds(5);
    public static final long REFRESH_TOKEN_TTL = TimeUnit.DAYS.toSeconds(7); // 7 days
    public static final long FORGOT_PASSWORD_TOKEN_TTL = TimeUnit.MINUTES.toSeconds(30);
    public static final long SESSION_TTL = TimeUnit.HOURS.toSeconds(2); // session duration: 2 hours
    public static final Duration REGISTER_LOCK_TIMEOUT_TTL = Duration.ofSeconds(5);
    public static final Duration OTP_EXPIRE_TTL = Duration.ofMinutes(5);
    public static final Duration RETRY_WINDOW_TTL = Duration.ofHours(1);
    public static final Duration FAILED_LOGIN_PREFIX_TTL = Duration.ofHours(30);
    public static final Duration LOGIN_CACHE_TTL = Duration.ofMinutes(30);
    public static final Duration LOGIN_LOCKOUT_ACCOUNT_TTL = Duration.ofMinutes(30);
}

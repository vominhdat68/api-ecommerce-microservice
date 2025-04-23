package com.ecommerce.shared_libs.cache.Redis;

import java.util.concurrent.TimeUnit;

public class RedisKeys {

    public static final String REGISTER_EMAIL_KEY ="otp:register:email:";
    public static final String VERIFY_OTP_EMAIL_KEY ="otp:email:";
    public static final String VERIFY_EMAIL_KEY ="auth:verify_email:";
    public static final String REFRESH_TOKEN_PREFIX = "auth:session:";
    public static final String JTI_PREFIX = "auth:jti:";
    public static final String REFRESH_USER_INDEX = "auth:refresh:index:"; // key này dùng cho Set -> phục vụ việc tìm kiếm nhanh
    public static final String REFRESH_USER_ORDER = "auth:refresh:index:%s:order";// key này dùng cho List -> lưu theo thứ tự thiết bị đăng nhập

    // TTL settings for Redis keys
    public static final long REGISTER_EMAIL_KEY_TTL = TimeUnit.HOURS.toSeconds(24);
    public static final long VERIFY_OTP_EMAIL_KEY_TTL = TimeUnit.MINUTES.toSeconds(5);
    public static final long REFRESH_TOKEN_TTL = TimeUnit.DAYS.toSeconds(7); // 7 days
    public static final long SESSION_TTL = TimeUnit.HOURS.toSeconds(2); // session duration: 2 hours


}

package com.ecommerce.user.util;

import org.springframework.http.ResponseCookie;

public class CookieUtils {

    public static ResponseCookie createHttpOnlyCookie(String name, String value, long maxAgeSeconds) {
        return ResponseCookie. from(name, value)
                .httpOnly(true)
                .secure(true) // bật nếu dùng HTTPS
                .path("/")
                .maxAge(maxAgeSeconds)
                .build();
    }

    public static ResponseCookie buildLogoutCookie() {
        return ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
//                .path("/api/v1/auth")
                .maxAge(0)
//                .sameSite("Strict")
//                .domain("yourdomain.com") // Thay bằng domain thực tế
                .build();
    }
}

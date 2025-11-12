package com.ecommerce.auth.dto.response;

import com.ecommerce.auth.dto.request.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token_access;
    private String token_refresh;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;
    private UserResponse user;
}

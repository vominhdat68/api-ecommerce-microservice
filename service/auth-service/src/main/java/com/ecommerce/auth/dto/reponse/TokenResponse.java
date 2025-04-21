package com.ecommerce.auth.dto.reponse;

import com.ecommerce.auth.dto.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String token_access;
    private String token_refresh;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;
    private UserDTO user;
}

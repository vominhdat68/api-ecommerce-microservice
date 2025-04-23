package com.ecommerce.auth.service;

import com.ecommerce.auth.cache.AuthCache;
import com.ecommerce.auth.cache.metadata.RefreshTokenMetadata;
import com.ecommerce.auth.dto.request.LoginRequest;
import com.ecommerce.auth.dto.request.RegisterRequest;
import com.ecommerce.auth.dto.request.UserResponse;
import com.ecommerce.auth.dto.response.TokenResponse;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.exception.InvalidTokenException;
import com.ecommerce.auth.config.security.JwtProvider;
import com.ecommerce.shared_libs.cache.Redis.OTPCache;
import com.ecommerce.shared_libs.cache.Redis.RedisKeys;
import com.ecommerce.shared_libs.response.ApiResponse;
import com.ecommerce.shared_libs.response.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthCache authCache;
    private final OTPCache otpCache;
    private static final int MAX_REFRESH_TOKENS = 5;

    public ApiResponse<?> register(RegisterRequest request) {
        if(userService.existsByUsername(request.username())){
            return ApiResponse.error(ResponseCode.REGISTER_USERNAME_EXISTS.getCode(),ResponseCode.REGISTER_USERNAME_EXISTS.getEnMessage());
        }
        if(userService.exitsByEmail(request.email())){
            return ApiResponse.error(ResponseCode.REGISTER_EMAIL_EXISTS.getCode(),ResponseCode.REGISTER_EMAIL_EXISTS.getEnMessage());
        }
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(false);
        userService.saveUser(user);
        //cache email verify OTP
        otpCache.cacheRegisteredEmail(request.email());

    return ApiResponse.success(ResponseCode.REGISTER_SUCCESS.getEnMessage());
    }


    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(),request.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return buildTokenResponse((UserDetails) authentication.getPrincipal());
    }
    /**
     * Refreshes authentication tokens by validating the old refresh token,
     * invalidating it, and generating new access/refresh tokens.
     *
     * @param refreshTokenOld The previous refresh token to validate and replace
     * @return TokenResponse containing new tokens, or null if invalid token
     */
    public ApiResponse<TokenResponse> refreshToken(String refreshTokenOld) {
        // check key va xoa di key cu
        if(!authCache.existKeyRefresh(refreshTokenOld)){
            return ApiResponse.error(ResponseCode.CACHE_REFRESH_TOKEN_NOT_FOUND.getCode(),ResponseCode.CACHE_REFRESH_TOKEN_NOT_FOUND.getEnMessage());
        }

        String username = authCache.extractUsernameFromRefresh(refreshTokenOld);
        UserDetails userDetails = userService.loadUserByUsername(username);
        // Build new response and invalidate old token
        String jtiOld = authCache.extractJtiFromRefresh(refreshTokenOld);
        authCache.deleteRefresh(refreshTokenOld);
        authCache.deleteKeysJti(jtiOld);
        return ApiResponse.success(buildTokenResponse(userDetails));
    }
    /**
     * Builds a complete token response including access/refresh tokens and metadata
     *
     * @param userDetails Authenticated user details
     * @return TokenResponse containing:
     *         - Newly generated JWT tokens
     *         - Expiration timestamps
     *         - User information
     *         - Cache entries for token management
     */
    private TokenResponse buildTokenResponse(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String jti = UUID.randomUUID().toString();
        // Generate tokens
        String accessToken = jwtProvider.generateAccessToken(userDetails, jti);
        String refreshToken = jwtProvider.generateRefreshToken();

        // Calculate expirations
        long expirationAccess = jwtProvider.extractExpirationToken(accessToken);
        long expirationRefresh = RedisKeys.REFRESH_TOKEN_TTL;

        // Build metadata
        RefreshTokenMetadata metadata = RefreshTokenMetadata.builder()
                .username(username)
                .jti(jti)
                .build();
        // Cache
        authCache.cacheRefreshToken(refreshToken, metadata, expirationRefresh);
        authCache.cacheJtiRevoke(jti,false,expirationAccess);

        UserResponse userResponse = new UserResponse(null,username,null,null);
        return TokenResponse.builder()
                .token_access(accessToken)
                .token_refresh(refreshToken)
                .accessTokenExpiry(expirationAccess)
                .refreshTokenExpiry(expirationRefresh)
                .user(userResponse)
                .build();
    }
    /**
     * Invalidates a refresh token by removing it from the cache storage
     * and cleaning up associated JWT keys.
     *
     * @param refreshToken The refresh token to invalidate
     * @throws InvalidTokenException If the token is invalid, not found,
     *         or if there's a data access failure
     */
    public ApiResponse<?> invalidateRefreshToken(String refreshToken){
            String jti = authCache.extractJtiFromRefresh(refreshToken);
            if (!StringUtils.hasText(jti)) {
                return ApiResponse.error(ResponseCode.GET_ME_UNAUTHORIZED.getCode(), ResponseCode.GET_ME_UNAUTHORIZED.getEnMessage());
            }

            authCache.deleteRefresh(refreshToken);
            authCache.deleteKeysJti(jti);
            return ApiResponse.success(null);
    }

}

package com.ecommerce.auth.service;

import com.ecommerce.auth.cache.AuthCache;
import com.ecommerce.auth.cache.metadata.RefreshTokenMetadata;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.dto.reponse.TokenResponse;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.enumMessage.RegisterStatus;
import com.ecommerce.auth.exception.InvalidTokenException;
import com.ecommerce.auth.security.JwtProvider;
import com.ecommerce.shared_libs.cache.Redis.RedisKeys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OtpVerificationService otpSender;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AuthCache authCache;
    private static final int MAX_REFRESH_TOKENS = 5;

    public RegisterResult register(RegisterRequest request) {
        if(userDetailsService.existsByUsername(request.getUsername())){
            return RegisterResult.builder().status(RegisterStatus.USERNAME_EXISTS).message("Username already exists").build();
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        userDetailsService.saveUser(user);
        otpSender.sendOtp(request.getEmail());
        return RegisterResult.builder().status(RegisterStatus.SUCCESS).message("Registration successful, please check your email for OTP").build();
    }


    public TokenResponse login(LoginRequest request, HttpServletRequest servletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
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
    public TokenResponse refreshToken(String refreshTokenOld) {
        // check key va xoa di key cu
        if(!authCache.existKeyRefresh(refreshTokenOld)){
            return null;
        }

        String username = authCache.extractUsernameFromRefresh(refreshTokenOld);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // Build new response and invalidate old token
        String jtiOld = authCache.extractJtiFromRefresh(refreshTokenOld);
        authCache.deleteRefresh(refreshTokenOld);
        authCache.deleteKeysJti(jtiOld);
        return buildTokenResponse(userDetails);
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

        return TokenResponse.builder()
                .token_access(accessToken)
                .token_refresh(refreshToken)
                .accessTokenExpiry(expirationAccess)
                .refreshTokenExpiry(expirationRefresh)
                .user(UserDTO.builder().name(username).build())
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
    public void invalidateRefreshToken(String refreshToken) throws InvalidTokenException {
        try {
            String jti = authCache.extractJtiFromRefresh(refreshToken);
            if (!StringUtils.hasText(jti)) {
                throw new InvalidTokenException("Refresh token not found");
            }

            authCache.deleteRefresh(refreshToken);
            authCache.deleteKeysJti(jti);
        } catch (DataAccessException ex){
            throw new InvalidTokenException("Failed to invalidate token");
        }

    }
}

package com.ecommerce.auth.service;

import com.ecommerce.auth.cache.AuthCache;
import com.ecommerce.auth.cache.metadata.RefreshTokenMetadata;
import com.ecommerce.auth.dto.request.*;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.exception.InvalidTokenException;
import com.ecommerce.auth.config.security.JwtProvider;
import com.ecommerce.user.cache.Redis.RedisKeys;
import com.ecommerce.user.response.ApiResponse;
import com.ecommerce.user.response.ResponseCode;
import com.ecommerce.user.util.OTPUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final AuthCache authCache;
    private final OtpService otpService;
    private final TransactionTemplate transactionTemplate;

    public ApiResponse<Object> register(RegisterRequest request) {
        String email = request.email();
        try {
            if (!authCache.acquireLock(email)) {
                return ApiResponse.error(ResponseCode.EMAIL_IS_IN_USE.getCode(),ResponseCode.EMAIL_IS_IN_USE.getEnMessage());
            }
            // Kiểm tra email tài khoản đang đăng ký có đang trong quá trình chờ xác thực ko?
            if (authCache.isAlreadyProcessing(email)) {
                return ApiResponse.error(ResponseCode.EMAIL_IS_IN_USE.getCode(),ResponseCode.EMAIL_IS_IN_USE.getEnMessage());
            }
        return  transactionTemplate.execute(status -> {

            //  Kiểm tra email tài khoản đang trong đăng ký
            if (userService.existsByUsername(email)) {
                return ApiResponse.error(ResponseCode.REGISTER_EMAIL_EXISTS.getCode(),ResponseCode.REGISTER_EMAIL_EXISTS.getEnMessage());
            }

            String otp = OTPUtil.generateSecureOtp();
            User pendingUser = userService.createPendingUser(request);
            authCache.cacheRegistrationData(email, otp, pendingUser);//cache otp để kiểm tra khi người dùng verify

            // Gửi OTP bất đồng bộ
            otpService.sendOtpEmailRegisterAsync(email, otp);

        return ApiResponse.success(ResponseCode.OTP_CHECK_EMAIL.getEnMessage());
        });
        } finally {
            authCache.releaseLock(email);
        }
    }




    public CompletableFuture<ApiResponse<?>> login(LoginRequest request, String clientIp) {
        //1. check verify với spring security quá trình này auto
        //...code....
        //2. Kiểm tra nếu tài khoản đang bị lock tạm thời -> đăng nhập sai quá nhiều
        if (authCache.isAccountLocked(request.username(), clientIp)) {
            return CompletableFuture.completedFuture(
                    ApiResponse.error(ResponseCode.ACCOUNT_LOCKED.getCode(), ResponseCode.ACCOUNT_LOCKED.getEnMessage())
            );
        }

        // 3. Xử lý bất đồng bộ các tác vụ tốn thời gian
        return CompletableFuture.supplyAsync(() -> {
            // 3.1. Kiểm tra user trong DB
            User user = userService.findByUsername(request.username());
            if (user == null || !userService.validPassword(request.password(), user.getPassword())) {
//      3.Rate limiting
                authCache.recordFailedLogin(request.username(), clientIp); // Tracking failed attempts
                return ApiResponse.error(ResponseCode.LOGIN_NOT_FOUND_ACCOUNT.getCode(),
                        ResponseCode.LOGIN_NOT_FOUND_ACCOUNT.getEnMessage());
            }

            return ApiResponse.success(buildTokenResponse(user));
        });
    }


    /**
     * Refreshes authentication tokens by validating the old refresh token,
     * invalidating it, and generating new access/refresh tokens.
     *
     * @param refreshTokenOld The previous refresh token to validate and replace
     * @return TokenResponse containing new tokens, or null if invalid token
     */
    public ApiResponse<LoginResponse> refreshToken(String refreshTokenOld) {
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
    private LoginResponse buildTokenResponse(UserDetails userDetails) {
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

        UserResponse userResponse = new UserResponse(username,username,null);
        return LoginResponse.builder()
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

            authCache.deleteRefresh(refreshToken);//xóa bỏ refresh token
            authCache.deleteKeysJti(jti);// block phiên đăng nhập
            return ApiResponse.success(null);
    }
/***
 * Kiem tra OTP tu client phan hoi
 *
 *
 */
    public ApiResponse<Object> verifyOtp(VerifyOtpRequest request) {
        String cachedOtp = authCache.getAndDeleteOtp(request.email());
        if (!authCache.isValidOtp(cachedOtp, request.otp())) {
            return ApiResponse.error(ResponseCode.OTP_VERIFICATION_FAILED.getCode(),ResponseCode.OTP_VERIFICATION_FAILED.getEnMessage());
        }

        // 2. Atomic lấy và xóa user data
        User user = authCache.getAndDeletePendingUser(request.email());
        if (user == null) {
            return ApiResponse.error(ResponseCode.OTP_EXPIRED.getCode(), ResponseCode.OTP_SUCCESS.getEnMessage());
        }

        if (!user.isEnabled()) {
            user.setEnabled(true);
            userService.saveUser(user);
        }
        // Clear OTP
        return ApiResponse.success(ResponseCode.OTP_SUCCESS.getEnMessage());
    }

    public ApiResponse<Object> resendOtp(ResendOtpRequest request) {

        int retryCount = authCache.getCurrentRetryCount(request.email());
        if (retryCount >= RedisKeys.MAX_RETRY) {
            return ApiResponse.error(ResponseCode.OTP_OVER_LIMIT.getCode(),ResponseCode.OTP_OVER_LIMIT.getEnMessage());
        }
        String newOtp = OTPUtil.generateSecureOtp();
        authCache.saveOtpAndUpdateRetryCount(request.email(), newOtp);
        otpService.sendOtpVerifyForgotPass(request.email(),newOtp);
        return ApiResponse.success(null);

    }
}

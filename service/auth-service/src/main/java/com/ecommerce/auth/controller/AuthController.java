package com.ecommerce.auth.controller;
import com.ecommerce.auth.dto.request.LoginRequest;
import com.ecommerce.auth.dto.request.RegisterRequest;
import com.ecommerce.auth.dto.request.ResendOtpRequest;
import com.ecommerce.auth.dto.request.VerifyOtpRequest;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.user.response.ApiResponse;
import com.ecommerce.user.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request){
        ApiResponse<Object> response = authService.register(request);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.message());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response.data().toString());
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<Object>> verifyEmail(@RequestBody @Valid VerifyOtpRequest request) {
        ApiResponse<Object> response = authService.verifyOtp(request);
        HttpStatus status = response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<ApiResponse<Object>> resendOtp(@RequestBody @Valid ResendOtpRequest request) {
    return ResponseEntity.ok(authService.resendOtp(request));
}

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<ApiResponse<?>>> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest httpRequest) {
        String clientIp = httpRequest.getRemoteAddr();
        return authService.login(loginRequest,clientIp)
                .thenApply(apiResponse  -> {
                    if(!apiResponse.isSuccess()){
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(apiResponse);
                    }

                    LoginResponse loginResponse = (LoginResponse) apiResponse.data();
                    // 1. Tạo HTTP-only cookies
                    ResponseCookie accessCookie = CookieUtils.createHttpOnlyCookie(
                            "access_token",
                            loginResponse.getToken_access(),
                            loginResponse.getAccessTokenExpiry()
                    );

                    ResponseCookie refreshCookie = CookieUtils.createHttpOnlyCookie(
                            "refresh_token",
                            loginResponse.getToken_refresh(),
                            loginResponse.getRefreshTokenExpiry()
                    );

                    // 3. Trả về response thành công (không chứa token trong body)
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE,accessCookie.toString(),refreshCookie.toString())
                            .body(ApiResponse.success(loginResponse.getUser()));
                });
    }

    /**
     * Handles refresh token requests to generate new access and refresh tokens
     *
     * @param refreshToken The refresh token from the HTTP-only cookie
     * @return ResponseEntity containing:
     *         - 201 Created with new tokens in cookies + user data if successful
     *         - 401 Unauthorized if token is missing or invalid
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@CookieValue(name = "refresh_token", required = false) String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ApiResponse<LoginResponse> response = authService.refreshToken(refreshToken);
        if (!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResponseCookie accessCookie = CookieUtils.createHttpOnlyCookie(
                "access_token", response.data().getToken_access(), response.data().getAccessTokenExpiry());
        ResponseCookie refreshCookie = CookieUtils.createHttpOnlyCookie(
                "refresh_token", response.data().getToken_refresh(), response.data().getRefreshTokenExpiry());

    return ResponseEntity.status(HttpStatus.CREATED)
            .header(HttpHeaders.SET_COOKIE,accessCookie.toString(),refreshCookie.toString())
            .body(response.data().getUser());
    }

    /**
     * Handles user logout requests
     *
     * @param refreshToken The refresh token value from cookie (if present)
     * @return ResponseEntity with:
     *         - 204 No Content on success (with cookie-clearing header)
     *         - 400 Bad Request if token is missing or invalid
     *         - 500 Internal Server Error for system failures
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@CookieValue(name = "refresh_token", required = false) String refreshToken){
            if (!StringUtils.hasText(refreshToken)) {
                return ResponseEntity.badRequest().build();
            }
            // Invalidate the refresh token
            ApiResponse<?> response = authService.invalidateRefreshToken(refreshToken);
            if(!response.isSuccess()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.message());
            }

            // Create cookie to clear the client-side token
            ResponseCookie clearCookie = CookieUtils.buildLogoutCookie();
            return ResponseEntity.noContent()
                    .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                    .build();

    }

}


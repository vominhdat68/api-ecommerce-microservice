package com.ecommerce.auth.controller;
import com.ecommerce.auth.dto.request.LoginRequest;
import com.ecommerce.auth.dto.request.RegisterRequest;
import com.ecommerce.auth.dto.request.VerifyEmailRequest;
import com.ecommerce.auth.dto.response.TokenResponse;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.OtpVerificationService;
import com.ecommerce.shared_libs.response.ApiResponse;
import com.ecommerce.shared_libs.util.CookieUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final OtpVerificationService otpVerify;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request){
        ApiResponse<?> response = authService.register(request);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getError().message());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response.data().toString());
    }

    @PostMapping("/send-verify-email")
    public ResponseEntity<String> sendEmail(@RequestParam String email){
        ApiResponse<Object> response = otpVerify.sendOtp(email);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getError().message());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Verify OTP successfully");
    }

    @GetMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyEmailRequest request){
        ApiResponse<Object> response = otpVerify.verifyEmail(request);
        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getError().message());
        }
        return ResponseEntity.status(HttpStatus.OK).body("Verify OTP successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        TokenResponse tokenResponse = authService.login(request);
        ResponseCookie accessCookie = CookieUtils.createHttpOnlyCookie("access_token", tokenResponse.getToken_access(), tokenResponse.getAccessTokenExpiry());
        ResponseCookie refreshCookie = CookieUtils.createHttpOnlyCookie("refresh_token", tokenResponse.getToken_refresh(), tokenResponse.getRefreshTokenExpiry());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString(), refreshCookie.toString())
                .body(tokenResponse.getUser());
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
        ApiResponse<TokenResponse> response = authService.refreshToken(refreshToken);
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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.getError().message());
            }

            // Create cookie to clear the client-side token
            ResponseCookie clearCookie = CookieUtils.buildLogoutCookie();
            return ResponseEntity.noContent()
                    .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                    .build();

    }

}


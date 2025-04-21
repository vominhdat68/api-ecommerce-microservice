package com.ecommerce.auth.controller;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.dto.reponse.TokenResponse;
import com.ecommerce.auth.exception.InvalidTokenException;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.OtpVerificationService;
import com.ecommerce.shared_libs.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request){
        RegisterResult result = authService.register(request);
        return switch (result.getStatus()){
            case SUCCESS -> ResponseEntity.ok(result.getMessage());
            case USERNAME_EXISTS, EMAIL_EXISTS ->
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getMessage());
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        };
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyEmailRequest request){
        otpVerify.verifyEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body("Verify OTP successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request, HttpServletRequest servletRequest){
        TokenResponse tokenResponse = authService.login(request, servletRequest);
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        TokenResponse response = authService.refreshToken(refreshToken);
        if (response == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        ResponseCookie accessCookie = CookieUtils.createHttpOnlyCookie(
                "access_token", response.getToken_access(), response.getAccessTokenExpiry());
        ResponseCookie refreshCookie = CookieUtils.createHttpOnlyCookie(
                "refresh_token", response.getToken_refresh(), response.getRefreshTokenExpiry());

    return ResponseEntity.status(HttpStatus.CREATED)
            .header(HttpHeaders.SET_COOKIE,accessCookie.toString(),refreshCookie.toString())
            .body(response.getUser());
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
    public ResponseEntity<Void> logout(@CookieValue(name = "refresh_token", required = false) String refreshToken){
        try {
            if (!StringUtils.hasText(refreshToken)) {
                return ResponseEntity.badRequest().build();
            }
            // Invalidate the refresh token
            authService.invalidateRefreshToken(refreshToken);

            // Create cookie to clear the client-side token
            ResponseCookie clearCookie = CookieUtils.buildLogoutCookie();
            return ResponseEntity.noContent()
                    .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                    .build();

        } catch (InvalidTokenException ex) {
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }


}


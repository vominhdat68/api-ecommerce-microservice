package com.ecommerce.auth.controller;
import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.dto.VerifyEmailRequest;
import com.ecommerce.auth.service.AuthService;
import com.ecommerce.auth.service.OtpVerificationService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        if (authService.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully");
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestBody @Valid VerifyEmailRequest request){
        otpVerify.verifyEmail(request);
        return ResponseEntity.status(HttpStatus.OK).body("Verify OTP successfully");
    }


}

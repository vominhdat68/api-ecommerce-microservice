package com.ecommerce.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerifyOtpRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "OTP cannot be blank")
        @Size(min = 6, max = 6, message = "OTP must be 6 digits")
        @Pattern(regexp = "\\d{6}", message = "OTP must contain only digits")
        String otp

) {}

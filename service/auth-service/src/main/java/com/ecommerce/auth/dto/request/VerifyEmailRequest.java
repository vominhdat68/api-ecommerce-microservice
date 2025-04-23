package com.ecommerce.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record VerifyEmailRequest(
    @Email(message = "Invalid email format")
    String email,
    @NotBlank(message = "OTP cannot be blank")
    @Size(min = 6, max = 6, message = "OTP must be exactly 6 characters")
    @Pattern(regexp = "[0-9]+", message = "OTP must contain only numbers")
    String otp
){}

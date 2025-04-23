package com.ecommerce.auth.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotBlank(message = "Username or email is required")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 8, max =40)
    String password
){}

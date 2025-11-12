package com.ecommerce.auth.dto.request;

public record UserResponse(
//        String publicId,
        String email,
        String fullName,
        String phone
) {}

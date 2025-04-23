package com.ecommerce.auth.dto.request;

public record UserResponse(
        Long id,
//        String publicId,
        String email,
        String fullName,
        String phone
) {}

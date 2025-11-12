package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.request.UpdateUserRequest;
import com.ecommerce.userservice.application.dto.response.UserResponse;
import com.ecommerce.userservice.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me")
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserResponse getCurrentUser() {

        return null;
    }

    @PutMapping
    public UserResponse updateProfile(@RequestBody @Valid UpdateUserRequest request) {

        return null;
    }

    @PostMapping("/avatar")
    public void updateAvatar(@RequestParam("avatarUrl") String avatarUrl) {}
}

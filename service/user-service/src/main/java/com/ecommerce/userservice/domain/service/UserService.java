package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.User;

public interface UserService {
    User getCurrentUser();
    User updateProfile(User user);
    void updateAvatar(Long userId, String avatarUrl);
}


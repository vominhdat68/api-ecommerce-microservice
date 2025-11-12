package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.UserLog;

import java.util.List;

public interface UserLogRepository {
    List<UserLog> findByUserId(Long userId);
    UserLog save(UserLog log);
}


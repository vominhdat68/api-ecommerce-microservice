package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.UserLog;

import java.util.List;

public interface UserLogService {
    List<UserLog> getLogsByUserId(Long userId);
    void logAction(UserLog log);
}

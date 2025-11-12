package com.ecommerce.userservice.infrastructure.repository.impl;

import com.ecommerce.userservice.domain.model.UserLog;
import com.ecommerce.userservice.domain.repository.UserLogRepository;
import com.ecommerce.userservice.infrastructure.repository.JpaUserLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserLogRepositoryImpl implements UserLogRepository {
    private final JpaUserLogRepository jpa;

    @Override
    public List<UserLog> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public UserLog save(UserLog log) {
        return null;
    }

    // Ánh xạ các phương thức tương tự như trên
}


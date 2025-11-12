package com.ecommerce.userservice.infrastructure.repository;

import com.ecommerce.userservice.infrastructure.entity.UserLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaUserLogRepository extends JpaRepository<UserLogEntity, Long> {
    List<UserLogEntity> findByUserId(Long userId);
}

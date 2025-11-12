package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    User save(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}


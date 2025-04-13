package com.ecommerce.auth.service;

import com.ecommerce.auth.dto.RegisterRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserRepository;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
    }

    public boolean existsByEmail(@Email(message = "Invalid email format") String email) {
        return userRepository.existsByEmail(email);
    }
}

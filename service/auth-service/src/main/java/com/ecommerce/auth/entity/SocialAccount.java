package com.ecommerce.auth.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "social_accounts")
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String provider; // "google", "facebook", etc.

    @Column(name = "provider_id")
    private String providerUserId;

//    @Lob
//    private String accessToken;
//
//    @Lob
//    private String refreshToken;

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt = LocalDateTime.now();
}

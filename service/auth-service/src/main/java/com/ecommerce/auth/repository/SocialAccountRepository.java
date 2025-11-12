package com.ecommerce.auth.repository;

import com.ecommerce.auth.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long> {
}

package com.ecommerce.userservice.infrastructure.repository;

import com.ecommerce.userservice.infrastructure.entity.WishlistItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaWishlistRepository extends JpaRepository<WishlistItemEntity, Long> {
    List<WishlistItemEntity> findByUserId(Long userId);
    Optional<WishlistItemEntity> findByUserIdAndProductId(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
}

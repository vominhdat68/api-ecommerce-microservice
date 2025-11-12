package com.ecommerce.userservice.infrastructure.repository;

import com.ecommerce.userservice.infrastructure.entity.RecentlyViewedProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaRecentlyViewedRepository extends JpaRepository<RecentlyViewedProductEntity, Long> {
    List<RecentlyViewedProductEntity> findTop10ByUserIdOrderByViewedAtDesc(Long userId);

    @Query("SELECT r FROM RecentlyViewedProductEntity r WHERE r.userId = :userId ORDER BY r.viewedAt ASC")
    Page<RecentlyViewedProductEntity> findOldestEntries(@Param("userId") Long userId, Pageable pageable);

}

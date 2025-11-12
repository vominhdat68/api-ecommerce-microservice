package com.ecommerce.productservice.infrastructure.repository;

import com.ecommerce.productservice.domain.model.Review;
import com.ecommerce.productservice.infrastructure.entity.ReviewEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductFullStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaReviewsRepository extends JpaRepository<ReviewEntity, Long> {
      @Query(value = """
              SELECT
                      p.id AS product_id,
                      p.name AS product_name,
                      ROUND(IFNULL(AVG(r.rating), 0), 1) AS avg_rating,
                      COUNT(r.product_id) AS total,
                      SUM(r.rating = 5) AS five_stars,
                      SUM(r.rating = 4) AS four_stars,
                      SUM(r.rating = 3) AS three_stars,
                      SUM(r.rating = 2) AS two_stars,
                      SUM(r.rating = 1) AS one_star
                  FROM reviews r
                  LEFT JOIN products p ON p.id = r.product_id
                  WHERE r.product_id = :productId
                  GROUP BY r.product_id;
              """,nativeQuery = true)
      Optional<ProductFullStatus> getFullStats(@Param("productId") Long productId);
      // Lọc theo productId + phân trang
      Page<ReviewEntity> findByProductId(Long productId, Pageable pageable);

      // Lọc theo productId + starRating + phân trang
      Page<ReviewEntity> findByProductIdAndRating(Long productId, Integer starRating, Pageable pageable);


      @Query(value = """
            SELECT 1
            FROM reviews
            WHERE user_id=:userId AND product_id=:productId
    """,nativeQuery = true)
    boolean existsByProductIdAndUserId(Long userId, Long productId);

}
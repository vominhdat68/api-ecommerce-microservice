package com.ecommerce.productservice.infrastructure.repository;

import com.ecommerce.productservice.infrastructure.entity.ProductEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.ProductProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findById(long id);

    @Query(value = """
        SELECT id, name, price, discount_price, stock_quantity
        FROM products
        WHERE category_id = :categoriesId AND is_active = true
    """, nativeQuery = true)
    Page<ProductProjection> findByProductsCategoryId(@Param("categoriesId") Long categoriesId, Pageable pageable);

    @Query(value = """
        SELECT id, name, price, discount_price, stock_quantity
        FROM products
        WHERE category_id =:categoriesId AND is_active = true
        AND price BETWEEN :minPrice AND :maxPrice
    """, nativeQuery = true)
    Page<ProductProjection> findByPriceBetweenProducts(Long categoriesId, Double minPrice, Double maxPrice, PageRequest pageRequest);

    @Query(value = """
            SELECT id, name, price, discount_price, stock_quantity
            FROM products WHERE brand_id =:brandId
            """, nativeQuery = true)
    Page<ProductProjection> findBrandWithProducts(@Param("brandId") Long brandId, Pageable pageable);
}
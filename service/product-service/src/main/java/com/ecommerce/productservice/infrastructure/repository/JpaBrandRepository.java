package com.ecommerce.productservice.infrastructure.repository;

import com.ecommerce.productservice.domain.model.Brand;
import com.ecommerce.productservice.infrastructure.entity.BrandEntity;
import com.ecommerce.productservice.infrastructure.repository.projection.BrandProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JpaBrandRepository extends JpaRepository<BrandEntity,Long> {

    @Query(value = """
            SELECT b.id,b.name,b.slug,b.logo_url,b.is_featured,COUNT(p.brand_id) AS productCount
            FROM brands b
            LEFT JOIN products p ON p.brand_id = b.id
            GROUP BY b.id
    """,nativeQuery = true)
    List<BrandProjection> findAllBrandsWithProductCount();

    @Query(value = """
            SELECT id,name,slug,logo_url,is_featured
            FROM brands WHERE id=:id
    """,nativeQuery = true)
    BrandProjection getBrandById(Long id);
}

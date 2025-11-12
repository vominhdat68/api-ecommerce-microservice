package com.ecommerce.productservice.infrastructure.repository;

import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.infrastructure.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity,Long> {

    @Query(value = """
        SELECT * FROM categories
        WHERE parent_id IS NULL AND is_active=true
    """,nativeQuery = true)
    List<CategoryEntity> findTopLevelCategory();

    @Query(value = """
        SELECT * FROM categories
        WHERE id=:categoriesId AND is_active=true
    """,nativeQuery = true)
    CategoryEntity findCategoryDetailQuery(Long categoriesId);
}

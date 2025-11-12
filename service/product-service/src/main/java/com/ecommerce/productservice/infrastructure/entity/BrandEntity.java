package com.ecommerce.productservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brands", uniqueConstraints = {
        @UniqueConstraint(name = "uq_brands_slug", columnNames = "slug")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(length = 255)
    private String website;

    @Column(name = "is_featured")
    private boolean isFeatured;
}
package com.ecommerce.productservice.infrastructure.repository.projection;
public interface BrandProjection {

    Long getId();

    String getName();

    String getSlug();

    String getLogoUrl();

    Boolean getIsFeatured();

    Integer getProductCount();

}


package com.ecommerce.productservice.infrastructure.repository.projection;

public interface ProductFullStatus {
    Long getProduct_id();

    String getProduct_name();

    Double getAvg_rating();

    Integer getTotal();

    Integer getFive_stars();

    Integer getFour_stars();

    Integer getThree_stars();

    Integer getTwo_stars();

    Integer getOne_star();

}
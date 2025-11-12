package com.ecommerce.cartservice.domain.repository;


import com.ecommerce.cartservice.domain.model.Cart;

import java.util.Optional;

public interface ICartDomainRepository {

    Optional<Cart> findByUserId(Long userId);

    void save(Cart cart);

    void deleteByUserId(Long userId);
}


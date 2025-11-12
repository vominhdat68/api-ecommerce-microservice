package com.ecommerce.cartservice.infrastructure.repository.impl;

import com.ecommerce.cartservice.application.mapper.CartMapper;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.repository.ICartDomainRepository;
import com.ecommerce.cartservice.infrastructure.repository.JpaCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements ICartDomainRepository {

    private final JpaCartRepository jpaCartRepository;


    @Override
    public Optional<Cart> findByUserId(Long userId) {
        return jpaCartRepository.findByUserId(userId)
                .map(CartMapper::toCart);
    }

    @Override
    public void save(Cart cart) {
        jpaCartRepository.save(CartMapper.toCartEntity(cart));
    }

    @Override
    public void deleteByUserId(Long userId) {
        jpaCartRepository.findByUserId(userId)
                .ifPresent(jpaCartRepository::delete);
    }
}


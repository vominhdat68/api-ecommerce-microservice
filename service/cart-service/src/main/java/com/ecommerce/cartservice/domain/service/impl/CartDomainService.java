package com.ecommerce.cartservice.domain.service.impl;

import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.repository.ICartDomainRepository;
import com.ecommerce.cartservice.domain.service.ICartDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartDomainService implements ICartDomainService {
    private final ICartDomainRepository cartDomainRepository;

    @Override
    public Cart getCart(Long userId) {
        return null;
    }

    @Override
    public void addItem(Long userId, AddItemRequest item) {

    }

    @Override
    public void updateItemQuantity(Long userId, Long productId, int quantity) {

    }

    @Override
    public void removeItem(Long userId, Long productId) {

    }

    @Override
    public void clearCart(Long userId) {

    }

    @Override
    public void addNote(Long userId, String note) {

    }
}

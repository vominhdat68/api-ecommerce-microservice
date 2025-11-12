package com.ecommerce.cartservice.application.service;


import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.domain.model.Cart;

public interface ICartApplicationService {

    Cart getCart(Long userId);

    void addItem(Long userId, AddItemRequest request);

    void updateItemQuantity(Long userId, Long itemId, Integer quantity);

    void removeItem(Long userId, Long itemId);

    void clearCart(Long userId);

    void addNote(Long userId, String note);
}

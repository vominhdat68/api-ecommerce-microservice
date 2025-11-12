package com.ecommerce.cartservice.domain.service;

import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.model.EstimatedCost;
import com.ecommerce.cartservice.domain.model.ShippingMethod;

import java.util.List;

public interface ICartDomainService {

    Cart getCart(Long userId);

    void addItem(Long userId, AddItemRequest item);

    void updateItemQuantity(Long userId, Long productId, int quantity);

    void removeItem(Long userId, Long productId);

    void clearCart(Long userId);

    void addNote(Long userId, String note);

//    void applyCoupon(Long userId, String couponCode);
//
//    void removeCoupon(Long userId);
//
//    void selectShippingMethod(Long userId, String shippingCode);
//
//    EstimatedCost estimateCost(Long userId);
//
//    List<ShippingMethod> getAvailableShippingMethods();
}


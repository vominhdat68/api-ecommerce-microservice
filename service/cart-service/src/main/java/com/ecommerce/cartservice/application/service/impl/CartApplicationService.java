package com.ecommerce.cartservice.application.service.impl;

import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.application.mapper.CartMapper;
import com.ecommerce.cartservice.application.service.ICartApplicationService;
import com.ecommerce.cartservice.domain.model.*;
import com.ecommerce.cartservice.domain.repository.ICartCacheRepository;
import com.ecommerce.cartservice.domain.repository.ICouponRepository;
import com.ecommerce.cartservice.domain.repository.IShippingMethodRepository;
import com.ecommerce.cartservice.domain.service.ICartDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartApplicationService implements ICartApplicationService {

    private final ICartDomainService iCartDomainService;
    private final ICartCacheRepository cartCacheRepository;
    private final ICouponRepository couponRepository;
    private final IShippingMethodRepository shippingMethodRepository;


    @Override
    public Cart getCart(Long userId) {


        return iCartDomainService.getCart(userId);
    }

    @Override
    public void addItem(Long userId, AddItemRequest item) {
        Cart cart;
        if ((cart = cartCacheRepository.getCart(userId).orElse(null)) == null){
            cart = new Cart();
        }
        cart.addItem(CartMapper.toCartItem(item));
        cartCacheRepository.saveCart(userId,cart);

        iCartDomainService.addItem(userId, item);
    }


    @Override
    public void updateItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = cartCacheRepository.getCart(userId).orElse(null);
        cartCacheRepository.updateItemQuantity(userId,productId, quantity);
//        cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long userId, Long productId) {
        cartCacheRepository.getCart(userId)
                .ifPresent(cart ->
                {
                    cart.removeItem(productId);
                    cart.calculateEstimatedCost();
                });
    }

    @Override
    public void clearCart(Long userId) {
        cartCacheRepository.deleteCart(userId);
    }


    @Override
    public void addNote(Long userId, String note) {
        cartCacheRepository.getCart(userId).ifPresent(
                cart -> {
                    cart.setNote(note);
                    cartCacheRepository.saveCart(userId, cart);
                });
    }

//    @Override
//    public void applyCoupon(Long userId, String couponCode) {
//        cartCacheRepository.getCart(userId).ifPresent(
//                cart -> {
//                    Coupon coupon = couponRepository.findByCode(couponCode)
//                            .orElseThrow(() -> new RuntimeException("Coupon not found"));
//                    cart.setCoupon(coupon);
//                    cartCacheRepository.saveCart(userId, cart);
//                });
//    }
//
//    @Override
//    public void removeCoupon(Long userId) {
//        cartCacheRepository.getCart(userId).ifPresent(
//                cart -> {
//                    cart.setCoupon(null);
//                    cartCacheRepository.saveCart(userId, cart);
//                });
//    }
//
//    @Override
//    public void selectShippingMethod(Long userId, String shippingCode) {
//        cartCacheRepository.getCart(userId).ifPresent(
//                cart -> {
//                    ShippingMethod method = shippingMethodRepository.findByCode(shippingCode)
//                            .orElseThrow(() -> new RuntimeException("Shipping method not found"));
//                    cart.setShippingMethod(method);
//                    cartCacheRepository.saveCart(userId, cart);
//                });
//    }
//
//    @Override
//    public EstimatedCost estimateCost(Long userId) {
//        return cartCacheRepository.getCart(userId)
//                .map(Cart::calculateEstimatedCost) // Nếu có Cart, tính toán EstimatedCost
//                .orElseThrow(() -> new RuntimeException("Cart EstimateCost not found for user: " + userId)); // Nếu không có, ném exception
//    }
//
//    @Override
//    public List<ShippingMethod> getAvailableShippingMethods() {
//        return shippingMethodRepository.findAll();
//    }
}

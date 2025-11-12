package com.ecommerce.cartservice.application.mapper;

import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.application.dto.response.CartItemResponse;
import com.ecommerce.cartservice.application.dto.response.CartResponse;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.cartservice.domain.model.CartItem;
import com.ecommerce.cartservice.domain.model.Coupon;
import com.ecommerce.cartservice.infrastructure.entity.CartEntity;
import com.ecommerce.cartservice.infrastructure.entity.CartItemEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {
    public static CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(CartMapper::toCartItemResponse)
                .collect(Collectors.toList());

        return new CartResponse(
                cart.getUserId(),
                items,
                cart.getNote(),
                cart.getAppliedCoupon() != null ? cart.getAppliedCoupon().getCode() : null,
                cart.getShippingMethod() != null ? cart.getShippingMethod().getCode() : null
        );
    }
    public static CartItemResponse toCartItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getProductId(),
                item.getName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }

    public static CartItem toCartItem(CartItemEntity item) {
        return new CartItem(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getPrice()
        );
    }

    public static CartItem toCartItem(AddItemRequest item) {
        return new CartItem(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice()
        );
    }

    public static CartEntity toCartEntity(Cart cart) {
        return CartEntity.builder()
                .userId(cart.getUserId())
                .note(cart.getNote())
                .couponCode(cart.getAppliedCoupon().getCode())
                .selectedShippingCode(cart.getShippingMethod().getName())
                .build();
    }

    public static Cart toCart(CartEntity cart) {
        List<CartItem> items = cart.getItems()
                .stream()
                .map(CartMapper::toCartItem)
                .collect(Collectors.toList());

        return Cart.builder()
                .userId(cart.getUserId())
                .items(items)
                .appliedCoupon(new Coupon(cart.getCouponCode()))
                .note(cart.getNote())
//                .shippingMethod(new ShippingMethod(cart.getSelectedShippingCode()))
                .build();
    }
}
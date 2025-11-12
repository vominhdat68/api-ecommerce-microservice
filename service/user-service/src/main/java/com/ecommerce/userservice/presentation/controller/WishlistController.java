package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.response.WishlistItemResponse;
import com.ecommerce.userservice.domain.service.WishlistItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/wishlist")
public class WishlistController {

    private final WishlistItemService wishlistService;

    @GetMapping
    public List<WishlistItemResponse> getWishlist() {

        return null;
    }
}

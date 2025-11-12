package com.ecommerce.cartservice.presentation.web;

import com.ecommerce.cartservice.application.dto.request.AddItemRequest;
import com.ecommerce.cartservice.application.dto.request.AddNoteRequest;
import com.ecommerce.cartservice.application.dto.request.UpdateCartItemRequest;
import com.ecommerce.cartservice.application.dto.response.CartResponse;
import com.ecommerce.cartservice.application.mapper.CartMapper;
import com.ecommerce.cartservice.application.service.ICartApplicationService;
import com.ecommerce.cartservice.domain.model.Cart;
import com.ecommerce.user.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ICartApplicationService iCartApplicationService;


    @GetMapping
    public ResponseEntity<CartResponse> viewCart(@RequestHeader("X-USER-ID") Long userId) {
        Cart cart = iCartApplicationService.getCart(userId);
        return ResponseEntity.ok(CartMapper.toCartResponse(cart));
    }

    @PostMapping
    public ResponseEntity<Void> addItem(@RequestHeader("X-USER-ID") Long userId,
                                        @RequestBody AddItemRequest request) {
        iCartApplicationService.addItem(userId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Void> updateQuantity(@RequestHeader("X-USER-ID") Long userId,
                                               @PathVariable Long itemId,
                                               @RequestBody UpdateCartItemRequest request) {
        iCartApplicationService.updateItemQuantity(userId, itemId, request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@RequestHeader("X-USER-ID") Long userId,
                                           @PathVariable Long itemId) {
        iCartApplicationService.removeItem(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-USER-ID") Long userId) {
        iCartApplicationService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/note")
    public ResponseEntity<Void> addNote(@RequestHeader("X-USER-ID") Long userId,
                                        @RequestBody AddNoteRequest request) {
        iCartApplicationService.addNote(userId, request.getNote());
        return ResponseEntity.ok().build();
    }

}

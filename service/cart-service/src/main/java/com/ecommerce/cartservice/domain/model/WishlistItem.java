package com.ecommerce.cartservice.domain.model;

import java.time.LocalDateTime;

public class WishlistItem {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime addedAt;
}

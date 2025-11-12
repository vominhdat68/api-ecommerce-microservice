package com.ecommerce.productservice.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class  Review {
    private Long id;
    private Product product;
    private Long userId;
    private String uerName;
    private int rating;
    private String comment;
    private boolean isApproved;
    private LocalDateTime createdAt;
    private String image;
}
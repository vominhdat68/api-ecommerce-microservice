package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.response.RecentlyViewedProductResponse;
import com.ecommerce.userservice.domain.service.RecentlyViewedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/recently-viewed")
public class RecentlyViewedController {

    private final RecentlyViewedService recentlyViewedService;

    @GetMapping
    public List<RecentlyViewedProductResponse> getRecentlyViewed() {

        return null;
    }
}


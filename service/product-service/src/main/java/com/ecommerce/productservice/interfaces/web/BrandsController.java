package com.ecommerce.productservice.interfaces.web;

import com.ecommerce.productservice.application.dto.response.BrandResponse;
import com.ecommerce.productservice.application.service.IBrandApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandsController {
    private final IBrandApplicationService iBrandApplicationService;

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getBrands(@RequestParam(value = "q", required = false) String brandName) {
        return ResponseEntity.ok(iBrandApplicationService.getBrandsByName(brandName));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<BrandResponse> getProductsByBrand(
            @PathVariable Long id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "20") int limit,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        return ResponseEntity.ok(iBrandApplicationService.getProductsByBrand(id, page, limit, sort));
    }
}

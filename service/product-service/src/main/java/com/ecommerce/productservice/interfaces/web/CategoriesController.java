package com.ecommerce.productservice.interfaces.web;

import com.ecommerce.productservice.application.dto.response.CategoryResponse;
import com.ecommerce.productservice.application.service.ICategoryApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final ICategoryApplicationService iCategoryApplicationService;


    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(iCategoryApplicationService.getCategoriesFirstLevel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryDetail(@PathVariable("id") Long categoriesId,
            @RequestParam(value = "page", required = false) Integer page) {
        return ResponseEntity.ok(iCategoryApplicationService.getCategoryDetail(categoriesId,page));
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<CategoryResponse> getCategoryByProducts(@PathVariable("id") Long categoriesId,
                                                              @RequestParam(value = "page", required = false) Integer page,
                                                                  @RequestParam(value = "limit", required = false) Integer limit,
                                                                  @RequestParam(value = "min_price", required = false) Double min_price,
                                                                  @RequestParam(value = "max_price", required = false) Double max_price,
                                                                  @RequestParam(value = "sort", required = false) String sort
                                                                  ) {
        return ResponseEntity.ok(iCategoryApplicationService.getProductsByCategoryId(categoriesId,page,limit,min_price,max_price,sort));
    }
}

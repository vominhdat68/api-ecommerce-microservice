package com.ecommerce.cartservice.presentation.web;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShippingController {

//    private final CartApplicationService cartService;
//
//
//    @GetMapping("/shipping-methods")
//    public ResponseEntity<?> getShippingMethods() {
//        var methods = cartService.getAvailableShippingMethods();
//        return ResponseEntity.ok(ShippingMethodMapper.toResponseList(methods));
//    }
//
//    @PostMapping("/select-shipping")
//    public ResponseEntity<Void> selectShipping(@RequestHeader("X-USER-ID") Long userId,
//                                               @RequestBody SelectShippingMethodRequest request) {
//        cartService.selectShippingMethod(userId, request.getShippingMethodCode());
//        return ResponseEntity.ok().build();
//    }
}


package com.ecommerce.userservice.presentation.controller;

import com.ecommerce.userservice.application.dto.request.AddressRequest;
import com.ecommerce.userservice.application.dto.response.AddressResponse;
import com.ecommerce.userservice.domain.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public List<AddressResponse> getAddresses() {

        return null;
    }

    @PostMapping
    public AddressResponse addAddress(@RequestBody @Valid AddressRequest request) {

        return null;
    }

    @PutMapping("/{id}")
    public AddressResponse updateAddress(@PathVariable Long id, @RequestBody @Valid AddressRequest request) {

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {}

    @PostMapping("/{id}/set-default")
    public void setDefaultAddress(@PathVariable Long id) {}
}

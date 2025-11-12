package com.ecommerce.userservice.domain.repository;

import com.ecommerce.userservice.domain.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {
    List<Address> findByUserId(Long userId);
    Optional<Address> findByIdAndUserId(Long id, Long userId);
    Address save(Address address);
    void deleteByIdAndUserId(Long id, Long userId);
    void unsetDefaultAddress(Long userId);
    void setDefaultAddress(Long id, Long userId);
}


package com.ecommerce.userservice.infrastructure.repository.impl;

import com.ecommerce.userservice.domain.model.Address;
import com.ecommerce.userservice.domain.repository.AddressRepository;
import com.ecommerce.userservice.infrastructure.repository.JpaAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository {
    private final JpaAddressRepository jpa;

    @Override
    public List<Address> findByUserId(Long userId) {
        return List.of();
    }

    @Override
    public Optional<Address> findByIdAndUserId(Long id, Long userId) {
        return Optional.empty();
    }

    @Override
    public Address save(Address address) {
        return null;
    }

    @Override
    public void deleteByIdAndUserId(Long id, Long userId) {

    }

    @Override
    public void unsetDefaultAddress(Long userId) {

    }

    @Override
    public void setDefaultAddress(Long id, Long userId) {

    }

    // Ánh xạ các phương thức tương tự như trên
}


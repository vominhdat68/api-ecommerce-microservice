package com.ecommerce.userservice.domain.service;

import com.ecommerce.userservice.domain.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAddressesByUserId(Long userId);
    Address addAddress(Address address);
    Address updateAddress(Long id, Address address);
    void deleteAddress(Long id);
    void setDefaultAddress(Long userId, Long addressId);
}


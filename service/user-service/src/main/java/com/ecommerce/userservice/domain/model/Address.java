package com.ecommerce.userservice.domain.model;

public class Address {
    private Long id;
    private Long userId;

    private String fullName;
    private String phoneNumber;
    private String province;
    private String district;
    private String ward;
    private String street;

    private boolean isDefault;

    public String getFullAddress() {
        return street + ", " + ward + ", " + district + ", " + province;
    }
}

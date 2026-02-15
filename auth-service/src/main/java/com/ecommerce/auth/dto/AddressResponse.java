package com.ecommerce.auth.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class AddressResponse {
    private Long id;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;
    private String addressType;
    private Boolean isDefault;
    private Instant createdAt;
    private Instant updatedAt;
    public AddressResponse(Long id, String streetAddress, String city, String state,
                           String postalCode, String country, String phoneNumber,
                           String addressType, Boolean isDefault, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.addressType = addressType;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
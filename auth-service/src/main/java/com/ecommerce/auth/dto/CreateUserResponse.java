package com.ecommerce.auth.dto;

import com.ecommerce.auth.domain.Address;
import lombok.Getter;

@Getter
public class CreateUserResponse {

    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final AddressResponse address;

    public CreateUserResponse(Long id,
                              String username,
                              String firstName,
                              String lastName,
                              String email,
                              AddressResponse address) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }


}

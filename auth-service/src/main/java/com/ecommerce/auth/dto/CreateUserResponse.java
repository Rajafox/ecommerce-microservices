package com.ecommerce.auth.dto;

public class CreateUserResponse {

    private final Long id;
    private final String username;

    public CreateUserResponse(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}

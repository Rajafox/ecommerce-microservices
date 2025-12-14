package com.ecommerce.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemRequest {

    private Long productId;
    private Integer quantity;

    // getters & setters
}
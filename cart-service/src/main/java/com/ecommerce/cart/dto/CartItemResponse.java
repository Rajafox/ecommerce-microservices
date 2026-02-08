package com.ecommerce.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponse {

    private Long productId;
    private Integer quantity;
    private BigDecimal cost;

    public CartItemResponse(Long productId, Integer quantity, BigDecimal cost) {
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    // getters
}

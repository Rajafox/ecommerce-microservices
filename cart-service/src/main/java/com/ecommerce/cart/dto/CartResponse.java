package com.ecommerce.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {

    private String userId;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;

    public CartResponse(String userId,
                        List<CartItemResponse> items,
                        BigDecimal totalAmount) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    // getters
}

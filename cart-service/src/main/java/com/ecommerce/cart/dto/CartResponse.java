package com.ecommerce.cart.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponse {

    private Long userId;
    private List<CartItemResponse> items;
    private BigDecimal totalAmount;

    public CartResponse(Long userId,
                        List<CartItemResponse> items,
                        BigDecimal totalAmount) {
        this.userId = userId;
        this.items = items;
        this.totalAmount = totalAmount;
    }


}

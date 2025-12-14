package com.ecommerce.order.dto;

public record CartItemResponse(
        Long productId,
        Integer quantity
) {}
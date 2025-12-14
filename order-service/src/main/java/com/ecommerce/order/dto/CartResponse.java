package com.ecommerce.order.dto;

import java.util.List;

public record CartResponse(
        String userId,
        List<CartItemResponse> items
) {}


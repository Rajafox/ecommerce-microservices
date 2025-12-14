package com.ecommerce.order.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        BigDecimal price,
        Integer stock
) {}

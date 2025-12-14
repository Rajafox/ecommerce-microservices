package com.ecommerce.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        BigDecimal price,
        Integer stock
) {}

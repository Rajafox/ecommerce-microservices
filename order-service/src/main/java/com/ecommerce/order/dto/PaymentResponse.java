package com.ecommerce.order.dto;

public record PaymentResponse(
        boolean success,
        String message,
        String paymentReference
) {}

package com.ecommerce.payment.dto;

public record PaymentResponse(
        boolean success,
        String message,
        String paymentReference
) {}

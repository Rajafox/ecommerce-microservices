package com.ecommerce.payment.service;

import com.ecommerce.payment.domain.Payment;
import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.dto.PaymentResponse;
import com.ecommerce.payment.dto.PaymentStatus;
import com.ecommerce.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public PaymentResponse processPayment(PaymentRequest request) {

        // Simulate payment logic
        boolean success = request.amount()
                .compareTo(BigDecimal.ZERO) > 0;

        Payment payment = new Payment();
        payment.setOrderId(request.orderId());
        payment.setAmount(request.amount());
        payment.setStatus(
                success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED
        );

        repository.save(payment);

        return new PaymentResponse(
                success,
                success ? "Payment successful" : "Payment failed"
        );
    }
}


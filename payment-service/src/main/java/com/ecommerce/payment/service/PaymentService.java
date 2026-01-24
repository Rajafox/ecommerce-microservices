package com.ecommerce.payment.service;

import com.ecommerce.payment.domain.Payment;
import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.dto.PaymentResponse;
import com.ecommerce.payment.dto.PaymentStatus;
import com.ecommerce.payment.repository.PaymentRepository;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        // 1️⃣ Idempotency check
        Optional<Payment> existing =
                repository.findByIdempotencyKey(request.idempotencyKey());

        if (existing.isPresent()) {
            Payment payment = existing.get();
            return new PaymentResponse(
                    payment.getStatus() == PaymentStatus.SUCCESS,
                    "Idempotent replay",
                    payment.getStripePaymentIntentId()
            );
        }

        try {
            // 2️⃣ Create Stripe PaymentIntent
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(
                                    request.amount()
                                            .multiply(BigDecimal.valueOf(100))
                                            .longValue() // paise
                            )
                            .setCurrency(request.currency().toLowerCase())
                            .putMetadata("orderId", request.orderId().toString())
                            .build();

            PaymentIntent intent = PaymentIntent.create(
                    params,
                    RequestOptions.builder()
                            .setIdempotencyKey(request.idempotencyKey())
                            .build()
            );

            // 3️⃣ Persist payment
            Payment payment = new Payment();
            payment.setOrderId(request.orderId());
            payment.setAmount(request.amount());
            payment.setCurrency(request.currency());
            payment.setIdempotencyKey(request.idempotencyKey());
            payment.setStripePaymentIntentId(intent.getId());
            payment.setStatus(PaymentStatus.SUCCESS);

            repository.save(payment);

            return new PaymentResponse(
                    true,
                    "Payment successful",
                    intent.getId()
            );

        } catch (Exception ex) {

            Payment payment = new Payment();
            payment.setOrderId(request.orderId());
            payment.setAmount(request.amount());
            payment.setCurrency(request.currency());
            payment.setIdempotencyKey(request.idempotencyKey());
            payment.setStatus(PaymentStatus.FAILED);

            repository.save(payment);

            return new PaymentResponse(false, "Payment failed", null);
        }
    }
}

package com.ecommerce.order.service;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderStatus;
import com.ecommerce.order.dto.*;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final RestTemplate restTemplate;

    @Value("${cart.service.url}")
    private String cartServiceUrl;

    @Value("${product.service.url}")
    private String productServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    public OrderService(OrderRepository repository,
                        RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Order createOrder(String userId) {

        // 1️⃣ Fetch cart
        CartResponse cart =
                restTemplate.getForObject(
                        cartServiceUrl + "/cart",
                        CartResponse.class
                );

        if (cart == null || cart.items().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Calculate total amount
        BigDecimal total = BigDecimal.ZERO;

        for (CartItemResponse item : cart.items()) {

            ProductResponse product =
                    restTemplate.getForObject(
                            productServiceUrl + "/products/" + item.productId(),
                            ProductResponse.class
                    );

            if (product == null || product.stock() < item.quantity()) {
                throw new RuntimeException("Invalid product or stock issue");
            }

            total = total.add(
                    product.price()
                            .multiply(BigDecimal.valueOf(item.quantity()))
            );
        }

        // 3️⃣ Create order (PENDING_PAYMENT)
        Order order = new Order();
        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        Order savedOrder = repository.save(order);

        String idempotencyKey = "order-" + savedOrder.getId();

        PaymentRequest paymentRequest =
                new PaymentRequest(
                        savedOrder.getId(),
                        total,
                        "INR",
                        idempotencyKey
                );

        PaymentResponse paymentResponse =
                restTemplate.postForObject(
                        paymentServiceUrl + "/payments",
                        paymentRequest,
                        PaymentResponse.class
                );

        // 5️⃣ Update order status
        if (paymentResponse != null && paymentResponse.success()) {
            savedOrder.setStatus(OrderStatus.PAID);
        } else {
            savedOrder.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        return repository.save(savedOrder);
    }

    /**
     * Fetch all orders of a user
     */
    public List<Order> getOrdersForUser(String userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Fetch order details (only owner can access)
     */
    public Order getOrderDetails(Long orderId, String userId) {
        return repository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found or access denied"));
    }
}


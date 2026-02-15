package com.ecommerce.order.service;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderStatus;
import com.ecommerce.order.dto.*;
import com.ecommerce.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    public Order createOrder(Long userId) {

        // 1️⃣ Fetch cart with authentication
        CartResponse cart = fetchCart(userId);

        if (cart == null || cart.items().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // 2️⃣ Calculate total amount
        BigDecimal total = BigDecimal.ZERO;

        for (CartItemResponse item : cart.items()) {

            ProductResponse product = fetchProduct(item.productId());

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

        // 4️⃣ Process payment with authentication
        String idempotencyKey = "order-" + savedOrder.getId();

        PaymentRequest paymentRequest =
                new PaymentRequest(
                        savedOrder.getId(),
                        total,
                        "USD",
                        idempotencyKey
                );

        PaymentResponse paymentResponse = fetchPayment(paymentRequest);

        // 5️⃣ Update order status
        if (paymentResponse != null && paymentResponse.success()) {
            savedOrder.setStatus(OrderStatus.PAID);

            // 6️⃣ Mark cart as inactive after successful payment
            try {
                deactivateCart(userId);
            } catch (Exception e) {
                // Log error but don't fail the order
                System.err.println("Failed to deactivate cart: " + e.getMessage());
            }
        } else {
            savedOrder.setStatus(OrderStatus.PAYMENT_FAILED);
        }

        return repository.save(savedOrder);
    }

    /**
     * Fetch all orders of a user
     */
    public List<Order> getOrdersForUser(Long userId) {
        return repository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    /**
     * Fetch order details (only owner can access)
     */
    public Order getOrderDetails(Long orderId, Long userId) {
        return repository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() ->
                        new RuntimeException("Order not found or access denied"));
    }

    /**
     * Helper method to get JWT token from current request and add to headers
     */
    private HttpHeaders createHeadersWithToken() {
        HttpHeaders headers = new HttpHeaders();

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            String authHeader = attributes.getRequest().getHeader("Authorization");
            if (authHeader != null) {
                headers.set("Authorization", authHeader);
            }
        }

        return headers;
    }

    /**
     * Helper method to fetch product with authentication
     */
    private ProductResponse fetchProduct(Long productId) {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                productServiceUrl + "/products/" + productId,
                HttpMethod.GET,
                entity,
                ProductResponse.class
        );

        return response.getBody();
    }

    /**
     * Helper method to fetch cart with authentication
     */
    private CartResponse fetchCart(Long userId) {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartResponse> response = restTemplate.exchange(
                cartServiceUrl + "/cart",
                HttpMethod.GET,
                entity,
                CartResponse.class
        );

        return response.getBody();
    }

    /**
     * Helper method to fetch payment with authentication
     */
    private PaymentResponse fetchPayment(PaymentRequest paymentRequest) {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentRequest, headers);

        ResponseEntity<PaymentResponse> response =
                restTemplate.exchange(
                        paymentServiceUrl + "/payments",
                        HttpMethod.POST,
                        entity,
                        PaymentResponse.class
                );
        return response.getBody();
    }

    /**
     * Helper method to deactivate cart after order placement
     */
    private void deactivateCart(Long userId) {
        HttpHeaders headers = createHeadersWithToken();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                cartServiceUrl + "/cart/deactivate",
                HttpMethod.POST,
                entity,
                Void.class
        );
    }
}


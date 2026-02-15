package com.ecommerce.order.controller;

import com.ecommerce.order.config.OpenApiConfig;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Orders")
@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME)
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    /**
     * Create new order (checkout)
     */
    @Operation(summary = "Create new order")
    @PostMapping
    public Order createOrder(Authentication authentication) {
        return service.createOrder(parseUserId(authentication));
    }

    /**
     * Fetch all orders of logged-in user
     */
    @Operation(summary = "Get all orders of user")
    @GetMapping
    public List<Order> getUserOrders(Authentication authentication) {
        return service.getOrdersForUser(parseUserId(authentication));
    }

    /**
     * Fetch order details by ID
     */
    @Operation(summary = "Get order details")
    @GetMapping("/{orderId}")
    public Order getOrder(
            @PathVariable Long orderId,
            Authentication authentication) {

        return service.getOrderDetails(orderId, parseUserId(authentication));
    }
    private Long parseUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }

}




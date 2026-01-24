package com.ecommerce.order.controller;

import com.ecommerce.order.config.OpenApiConfig;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "Orders")
@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME)
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "Checkout current user's cart")
    @PostMapping("/checkout")
    public Order checkout(Authentication auth) {
        return service.checkout(auth.getName());
    }

    //TODO: list all orders from user,
}




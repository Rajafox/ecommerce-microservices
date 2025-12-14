package com.ecommerce.cart.controller;

import com.ecommerce.cart.config.OpenApiConfig;
import com.ecommerce.cart.domain.Cart;
import com.ecommerce.cart.dto.AddCartItemRequest;
import com.ecommerce.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Cart")
@RestController
@RequestMapping("/cart")
@SecurityRequirement(name = OpenApiConfig.SECURITY_SCHEME)
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @Operation(summary = "Get current user's cart")
    @GetMapping
    public Cart getCart(Authentication auth) {
        return service.getCart(auth.getName());
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/items")
    public Cart addItem(
            Authentication auth,
            @RequestBody AddCartItemRequest request) {

        return service.addItem(auth.getName(), request);
    }
}


package com.ecommerce.cart.controller;

import com.ecommerce.cart.api.ApiMessages;
import com.ecommerce.cart.api.ApiResponse;
import com.ecommerce.cart.config.OpenApiConfig;
import com.ecommerce.cart.domain.Cart;
import com.ecommerce.cart.dto.AddCartItemRequest;
import com.ecommerce.cart.dto.CartResponse;
import com.ecommerce.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    /**
     * Get cart with total amount
     */
    @GetMapping
    public CartResponse getCart(Authentication authentication) {
        return service.getCartWithTotal(parseUserId(authentication));
    }

    /**
     * Clear cart
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        service.clearCart(parseUserId(authentication));
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.CART_CLEARED, null));
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/items")
    public CartResponse addItem(
            Authentication auth,
            @RequestBody AddCartItemRequest request) {

        service.addItem(parseUserId(auth), request);
        return service.getCartWithTotal(parseUserId(auth));
    }

    @Operation(summary = "Remove item from cart")
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeItem(
            Authentication auth,
            @Parameter(name = "productId", in = ParameterIn.PATH, description = "Product ID", required = true)
            @PathVariable("productId")  Long productId) {

        service.removeItem(parseUserId(auth), productId);
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.ITEM_REMOVED, null));
    }

    @Operation(summary = "Update item quantity in cart")
    @PutMapping("/items/{productId}")
    public ResponseEntity<Cart> updateItemQuantity(
            Authentication auth,
            @Parameter(name = "productId", in = ParameterIn.PATH, description = "Product ID", required = true)
            @PathVariable("productId")  Long productId,
            @RequestParam Integer quantity) {

        Cart cart = service.updateItemQuantity(parseUserId(auth), productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @Operation(summary = "Mark cart as inactive (used after order placement)")
    @PostMapping("/deactivate")
    public ResponseEntity<ApiResponse<Void>> deactivateCart(Authentication auth) {
        service.markCartAsInactive(parseUserId(auth));
        return ResponseEntity.ok(ApiResponse.success(ApiMessages.CART_DEACTIVATED, null));
    }

    private Long parseUserId(Authentication authentication) {
        return Long.parseLong(authentication.getName());
    }
}

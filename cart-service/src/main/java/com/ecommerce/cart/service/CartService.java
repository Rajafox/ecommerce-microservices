package com.ecommerce.cart.service;


import com.ecommerce.cart.domain.Cart;
import com.ecommerce.cart.domain.CartItem;
import com.ecommerce.cart.dto.AddCartItemRequest;
import com.ecommerce.cart.dto.CartItemResponse;
import com.ecommerce.cart.dto.CartResponse;
import com.ecommerce.cart.dto.ProductResponse;
import com.ecommerce.cart.repository.CartRepository;
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
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository repository;
    private final RestTemplate restTemplate;
    private final String productServiceUrl;

    public CartService(CartRepository repository,
                       RestTemplate restTemplate,
                       @Value("${product.service.url}") String productServiceUrl) {
        this.repository = repository;
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
    }

    public Cart getCart(Long userId) {
        return repository.findByUserIdAndIsActive(userId, true)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    cart.setIsActive(true);
                    return repository.save(cart);
                });
    }

    /**
     * Fetch cart with total amount
     */
    public CartResponse getCartWithTotal(Long userId) {

        Cart cart = repository.findByUserIdAndIsActive(userId, true)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUserId(userId);
                    c.setIsActive(true);
                    return repository.save(c);
                });


        List<CartItemResponse> items =
                cart.getItems().stream().map(item -> {

                    ProductResponse product = fetchProduct(item.getProductId());

                    if (product == null) {
                        throw new RuntimeException("Product not found: " + item.getProductId());
                    }

                    return new CartItemResponse(
                            item.getProductId(),
                            item.getQuantity(),
                            product.price().multiply(BigDecimal.valueOf(item.getQuantity()))
                    );
                }).toList();
        BigDecimal total = items.stream().map(CartItemResponse::getCost).reduce(BigDecimal.ZERO,BigDecimal::add);

        return new CartResponse(cart.getUserId(), items, total);
    }

    /**
     * Clear cart
     */
    public void clearCart(Long userId) {

        Cart cart = repository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
        repository.save(cart);
    }

    public Cart addItem(Long userId, AddCartItemRequest request) {

        // 🔍 Validate product exists
        ProductResponse product = fetchProduct(request.getProductId());

        if (product == null || product.stock() < request.getQuantity()) {
            throw new RuntimeException("Invalid product or insufficient stock");
        }

        Cart cart = getCart(userId);

        Optional<CartItem> existing =
                cart.getItems()
                        .stream()
                        .filter(i -> i.getProductId()
                                .equals(request.getProductId()))
                        .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(
                    existing.get().getQuantity() + request.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setProductId(request.getProductId());
            item.setQuantity(request.getQuantity());
            item.setCart(cart);
            cart.getItems().add(item);
        }

        return repository.save(cart);
    }

    /**
     * Remove item from cart
     */
    public void removeItem(Long userId, Long productId) {
        Cart cart = repository.findByUserIdAndIsActive(userId, true)
                .orElseThrow(() -> new RuntimeException("Active cart not found"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        repository.save(cart);
    }

    /**
     * Update item quantity in cart
     */
    public Cart updateItemQuantity(Long userId, Long productId, Integer quantity) {
        Cart cart = repository.findByUserIdAndIsActive(userId, true)
                .orElseThrow(() -> new RuntimeException("Active cart not found"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        } else {
            // Validate stock
            ProductResponse product = fetchProduct(productId);
            if (product == null || product.stock() < quantity) {
                throw new RuntimeException("Insufficient stock");
            }
            item.setQuantity(quantity);
        }

        return repository.save(cart);
    }

    /**
     * Mark cart as inactive (used after order placement)
     */
    public void markCartAsInactive(Long userId) {
        Cart cart = repository.findByUserIdAndIsActive(userId, true)
                .orElseThrow(() -> new RuntimeException("Active cart not found"));

        cart.setIsActive(false);
        repository.save(cart);
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
}

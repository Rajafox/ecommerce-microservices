package com.ecommerce.cart.service;


import com.ecommerce.cart.domain.Cart;
import com.ecommerce.cart.domain.CartItem;
import com.ecommerce.cart.dto.AddCartItemRequest;
import com.ecommerce.cart.dto.CartItemResponse;
import com.ecommerce.cart.dto.CartResponse;
import com.ecommerce.cart.dto.ProductResponse;
import com.ecommerce.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository repository;
    private final RestTemplate restTemplate;

    @Value("${product.service.url}")
    private String productServiceUrl;

    public CartService(CartRepository repository,
                       RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Cart getCart(Long userId) {
        return repository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return repository.save(cart);
                });
    }

    /**
     * Fetch cart with total amount
     */
    public CartResponse getCartWithTotal(Long userId) {

        Cart cart = repository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUserId(userId);
                    return repository.save(c);
                });


        List<CartItemResponse> items =
                cart.getItems().stream().map(item -> {

                    ProductResponse product =
                            restTemplate.getForObject(
                                    productServiceUrl + "/products/" + item.getProductId(),
                                    ProductResponse.class
                            );

                    return new CartItemResponse(
                            item.getProductId(),
                            item.getQuantity(),product.price()
                            .multiply(BigDecimal.valueOf(item.getQuantity()))
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
        ProductResponse product =
                restTemplate.getForObject(
                        productServiceUrl + "/products/" + request.getProductId(),
                        ProductResponse.class
                );

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
}

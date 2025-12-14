package com.ecommerce.cart.service;

import com.ecommerce.cart.CartRepository;
import com.ecommerce.cart.domain.Cart;
import com.ecommerce.cart.domain.CartItem;
import com.ecommerce.cart.dto.AddCartItemRequest;
import com.ecommerce.cart.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public Cart getCart(String userId) {
        return repository.findById(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    return repository.save(cart);
                });
    }

    public Cart addItem(String userId, AddCartItemRequest request) {

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

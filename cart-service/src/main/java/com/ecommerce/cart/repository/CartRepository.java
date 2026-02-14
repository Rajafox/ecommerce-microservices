package com.ecommerce.cart.repository;

import com.ecommerce.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository
        extends JpaRepository<Cart, Long> {
        Optional<Cart> findByUserId(Long userId);
        Optional<Cart> findByUserIdAndIsActive(Long userId, Boolean isActive);
    }

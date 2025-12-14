package com.ecommerce.cart;

import com.ecommerce.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository
        extends JpaRepository<Cart, String> {
}


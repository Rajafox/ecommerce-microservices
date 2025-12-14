CREATE TABLE carts (
    user_id VARCHAR(100) PRIMARY KEY
);

CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    cart_id VARCHAR(100),
    CONSTRAINT fk_cart
        FOREIGN KEY (cart_id)
        REFERENCES carts(user_id)
        ON DELETE CASCADE
);
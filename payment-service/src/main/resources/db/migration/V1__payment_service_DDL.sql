CREATE TABLE currencies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(5),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) NOT NULL,
    stripe_payment_id VARCHAR(100) NOT NULL,
    idempotency_key VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_payment_currency
    FOREIGN KEY (currency) REFERENCES currencies(code)
);

CREATE UNIQUE INDEX idx_payments_stripe_payment_id ON payments(stripe_payment_id);
CREATE UNIQUE INDEX idx_payments_idempotency_key ON payments(idempotency_key);
CREATE INDEX idx_payments_order_id ON payments(order_id);
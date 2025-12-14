
-- =========================
-- PRODUCTS
-- =========================

INSERT INTO products (name, description, price, stock)
VALUES
('iPhone 15', 'Apple iPhone 15 128GB', 79999.00, 20),
('Samsung Galaxy S24', 'Samsung flagship phone', 69999.00, 15),
('MacBook Air M2', 'Apple MacBook Air with M2 chip', 119999.00, 10),
('Sony WH-1000XM5', 'Noise cancelling headphones', 29999.00, 30),
('Logitech MX Master 3S', 'Wireless mouse', 9999.00, 50);


-- =========================
-- CARTS
-- =========================

INSERT INTO carts (user_id)
VALUES
('admin'),
('user');

-- =========================
-- CART ITEMS
-- =========================

INSERT INTO cart_items (product_id, quantity, cart_id)
VALUES
(1, 1, 'admin'),  -- iPhone 15
(4, 2, 'admin'),  -- Headphones
(2, 1, 'user');   -- Samsung Galaxy


-- =========================
-- ORDERS
-- =========================

INSERT INTO orders (user_id, total_amount, status)
VALUES
('admin', 139997.00, 'COMPLETED'),
('user', 69999.00, 'CREATED'),
('user', 29999.00, 'CANCELLED');
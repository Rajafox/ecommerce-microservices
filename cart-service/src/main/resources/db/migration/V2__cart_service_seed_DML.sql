-- =========================
-- CARTS
-- =========================
-- Note: user_id references users table from auth-service
-- product_id in cart_items references products table from product-service

-- Active cart for user1 (user_id: 2)
INSERT INTO carts (user_id, is_active) VALUES
(2, TRUE);

-- Inactive carts for user1 (previously placed orders)
INSERT INTO carts (user_id, is_active) VALUES
(2, FALSE),
(2, FALSE);

-- Active cart for admin (user_id: 1)
INSERT INTO carts (user_id, is_active) VALUES
(1, TRUE);

-- =========================
-- CART ITEMS
-- =========================

-- Cart 1: Active cart for user1 with 5 items
INSERT INTO cart_items (product_id, quantity, cart_id) VALUES
-- iPhone 15 Pro (product_id: 1)
(1, 1, 1),
-- AirPods Pro 2nd Gen (product_id: 18)
(18, 1, 1),
-- Apple Watch Series 9 (product_id: 27)
(27, 1, 1),
-- Anker PowerCore 20000 (product_id: 24)
(24, 2, 1),
-- Logitech MX Master 3S (product_id: 22)
(22, 1, 1);

-- Cart 2: Inactive cart for user1 (past order - laptops and accessories)
INSERT INTO cart_items (product_id, quantity, cart_id) VALUES
-- MacBook Air M3 (product_id: 7)
(7, 1, 2),
-- Apple Magic Keyboard (product_id: 23)
(23, 1, 2),
-- Logitech MX Master 3S (product_id: 22)
(22, 1, 2);

-- Cart 3: Inactive cart for user1 (past order - audio equipment)
INSERT INTO cart_items (product_id, quantity, cart_id) VALUES
-- Sony WH-1000XM5 (product_id: 17)
(17, 1, 3),
-- JBL Flip 6 (product_id: 20)
(20, 2, 3);

-- Cart 4: Active cart for admin with 3 items
INSERT INTO cart_items (product_id, quantity, cart_id) VALUES
-- Samsung Galaxy S24 Ultra (product_id: 3)
(3, 1, 4),
-- Samsung Galaxy Watch 6 (product_id: 28)
(28, 1, 4),
-- Samsung T7 Shield SSD (product_id: 26)
(26, 2, 4);


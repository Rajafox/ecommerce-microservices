-- =========================
-- ORDERS
-- =========================
-- Note: user_id references users table from auth-service
-- product_id in order_items references products table from product-service
-- These orders correspond to the inactive carts in cart-service
-- Valid statuses: CREATED, PENDING_PAYMENT, PAID, PAYMENT_FAILED

-- Order 1: Paid order for user1 (Laptop & accessories)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(2, 134898.00, 'PAID', '2026-01-15 10:30:00');

-- Order 2: Paid order for user1 (Audio equipment)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(2, 53997.00, 'PAID', '2026-01-20 14:45:00');

-- Order 3: Pending payment order for user1 (Smartphone bundle)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(2, 79999.00, 'PENDING_PAYMENT', '2026-02-10 09:15:00');

-- Order 4: Paid order for admin (Tablet & accessories)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(1, 89898.00, 'PAID', '2026-02-12 16:20:00');

-- Order 5: Created order for admin (Gaming console)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(1, 49999.00, 'CREATED', '2026-02-14 11:00:00');

-- Order 6: Payment failed order for user1
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(2, 249999.00, 'PAYMENT_FAILED', '2026-01-25 13:30:00');

-- Order 7: Paid order for admin (Audio & accessories)
INSERT INTO orders (user_id, total_amount, status, created_at) VALUES
(1, 44998.00, 'PAID', '2026-01-18 15:45:00');

-- =========================
-- ORDER ITEMS
-- =========================

-- Order 1 Items: Laptop & accessories (Total: 134,898)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- MacBook Air M3 (114,900)
(7, 1, 1),
-- Apple Magic Keyboard (9,900)
(23, 1, 1),
-- Logitech MX Master 3S (9,999)
(22, 1, 1);

-- Order 2 Items: Audio equipment (Total: 53,997)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- Sony WH-1000XM5 (29,999)
(17, 1, 2),
-- JBL Flip 6 (11,999 x 2 = 23,998)
(20, 2, 2);

-- Order 3 Items: Smartphone (Total: 79,999)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- Samsung Galaxy S24 (79,999)
(4, 1, 3);

-- Order 4 Items: Tablet & accessories (Total: 89,898)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- iPad Air (59,900)
(14, 1, 4),
-- Apple Magic Keyboard (9,900)
(23, 1, 4),
-- Anker PowerCore 20000 (3,999 x 5 = 19,995)
(24, 5, 4);

-- Order 5 Items: Gaming console (Total: 49,999)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- PlayStation 5 (49,999)
(31, 1, 5);

-- Order 6 Items: Camera (Cancelled) (Total: 249,999)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- Canon EOS R6 Mark II (249,999)
(36, 1, 6);

-- Order 7 Items: Audio & accessories (Total: 44,998)
INSERT INTO order_items (product_id, quantity, order_id) VALUES
-- Bose QuietComfort Ultra (34,999)
(19, 1, 7),
-- Logitech MX Master 3S (9,999)
(22, 1, 7);



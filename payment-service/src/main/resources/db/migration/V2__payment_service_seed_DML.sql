-- =========================
-- CURRENCIES
-- =========================

INSERT INTO currencies (code, name, symbol, is_active) VALUES
('USD', 'United States Dollar', '$', TRUE),
('EUR', 'Euro', '€', TRUE),
('GBP', 'British Pound', '£', TRUE),
('JPY', 'Japanese Yen', '¥', TRUE),
('CAD', 'Canadian Dollar', 'CA$', TRUE),
('AUD', 'Australian Dollar', 'A$', TRUE),
('INR', 'Indian Rupee', '₹', TRUE),
('CNY', 'Chinese Yuan', '¥', TRUE),
('CHF', 'Swiss Franc', 'CHF', TRUE),
('SGD', 'Singapore Dollar', 'S$', TRUE),
('AED', 'UAE Dirham', 'د.إ', TRUE),
('NZD', 'New Zealand Dollar', 'NZ$', TRUE);

-- =========================
-- PAYMENTS
-- =========================
-- Note: order_id references orders table from order-service
-- These payments correspond to orders with PAID and PAYMENT_FAILED statuses
-- Valid payment statuses: succeeded, pending, failed, canceled, requires_payment_method

-- Payment 1: Successful payment for Order 1 (Laptop & accessories) - INR 134,898
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(1, 134898.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd01', 'idem_order_1_2026_01_15_10_30_00', 'succeeded', '2026-01-15 10:35:00');

-- Payment 2: Successful payment for Order 2 (Audio equipment) - INR 53,997
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(2, 53997.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd02', 'idem_order_2_2026_01_20_14_45_00', 'succeeded', '2026-01-20 14:50:00');

-- Payment 3: Pending payment for Order 3 (Smartphone) - INR 79,999
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(3, 79999.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd03', 'idem_order_3_2026_02_10_09_15_00', 'pending', '2026-02-10 09:20:00');

-- Payment 4: Successful payment for Order 4 (Tablet & accessories) - INR 89,898
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(4, 89898.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd04', 'idem_order_4_2026_02_12_16_20_00', 'succeeded', '2026-02-12 16:25:00');

-- Payment 5: Failed payment for Order 6 (Camera) - INR 249,999
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(6, 249999.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd05', 'idem_order_6_2026_01_25_13_30_00', 'failed', '2026-01-25 13:35:00');

-- Payment 6: Successful payment for Order 7 (Audio & accessories) - INR 44,998
INSERT INTO payments (order_id, amount, currency, stripe_payment_id, idempotency_key, status, created_at) VALUES
(7, 44998.00, 'INR', 'pi_3QABCDEFGHIJKLMNOPQRSTUVWXYZabcd06', 'idem_order_7_2026_01_18_15_45_00', 'succeeded', '2026-01-18 15:50:00');


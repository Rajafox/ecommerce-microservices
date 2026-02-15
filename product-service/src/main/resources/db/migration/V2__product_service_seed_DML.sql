-- =========================
-- CATEGORIES
-- =========================

INSERT INTO categories (name) VALUES
('Smartphones'),
('Laptops'),
('Tablets'),
('Audio'),
('Accessories'),
('Wearables'),
('Gaming'),
('Cameras');

-- =========================
-- PRODUCTS
-- =========================

INSERT INTO products (name, description, price, stock, brand, category_id) VALUES
-- Smartphones
('iPhone 15 Pro', 'Apple iPhone 15 Pro 256GB with A17 Pro chip', 129999.00, 25, 'Apple', 1),
('iPhone 15', 'Apple iPhone 15 128GB', 79999.00, 30, 'Apple', 1),
('Samsung Galaxy S24 Ultra', 'Samsung flagship phone with S Pen', 124999.00, 20, 'Samsung', 1),
('Samsung Galaxy S24', 'Samsung Galaxy S24 5G', 79999.00, 35, 'Samsung', 1),
('Google Pixel 8 Pro', 'Google Pixel 8 Pro with AI features', 99999.00, 15, 'Google', 1),
('OnePlus 12', 'OnePlus flagship with Snapdragon 8 Gen 3', 64999.00, 40, 'OnePlus', 1),

-- Laptops
('MacBook Air M3', 'Apple MacBook Air 13" with M3 chip', 114900.00, 18, 'Apple', 2),
('MacBook Pro 14"', 'Apple MacBook Pro 14" with M3 Pro chip', 199900.00, 12, 'Apple', 2),
('Dell XPS 15', 'Dell XPS 15 with Intel Core i7', 149999.00, 10, 'Dell', 2),
('HP Spectre x360', 'HP convertible laptop 2-in-1', 129999.00, 8, 'HP', 2),
('Lenovo ThinkPad X1', 'Lenovo ThinkPad X1 Carbon Gen 11', 139999.00, 15, 'Lenovo', 2),
('ASUS ROG Zephyrus', 'ASUS gaming laptop with RTX 4070', 179999.00, 7, 'ASUS', 2),

-- Tablets
('iPad Pro 12.9"', 'Apple iPad Pro 12.9" with M2 chip', 109900.00, 20, 'Apple', 3),
('iPad Air', 'Apple iPad Air 10.9" with M1 chip', 59900.00, 25, 'Apple', 3),
('Samsung Galaxy Tab S9', 'Samsung premium tablet with S Pen', 79999.00, 18, 'Samsung', 3),
('Microsoft Surface Pro 9', 'Microsoft 2-in-1 tablet', 99999.00, 12, 'Microsoft', 3),

-- Audio
('Sony WH-1000XM5', 'Premium noise cancelling headphones', 29999.00, 45, 'Sony', 4),
('AirPods Pro 2nd Gen', 'Apple wireless earbuds with ANC', 24900.00, 60, 'Apple', 4),
('Bose QuietComfort Ultra', 'Bose premium ANC headphones', 34999.00, 30, 'Bose', 4),
('JBL Flip 6', 'Portable Bluetooth speaker', 11999.00, 50, 'JBL', 4),
('Sonos One', 'Smart speaker with Alexa', 19999.00, 25, 'Sonos', 4),

-- Accessories
('Logitech MX Master 3S', 'Wireless ergonomic mouse', 9999.00, 70, 'Logitech', 5),
('Apple Magic Keyboard', 'Wireless keyboard for Mac', 9900.00, 40, 'Apple', 5),
('Anker PowerCore 20000', 'Portable charger 20000mAh', 3999.00, 100, 'Anker', 5),
('Belkin USB-C Hub', '7-in-1 multiport adapter', 5999.00, 55, 'Belkin', 5),
('Samsung T7 Shield SSD', 'Portable SSD 1TB', 12999.00, 35, 'Samsung', 5),

-- Wearables
('Apple Watch Series 9', 'Apple smartwatch with health features', 41900.00, 40, 'Apple', 6),
('Samsung Galaxy Watch 6', 'Samsung smartwatch with Wear OS', 29999.00, 35, 'Samsung', 6),
('Fitbit Charge 6', 'Fitness tracker with GPS', 12999.00, 50, 'Fitbit', 6),
('Garmin Forerunner 265', 'GPS running watch', 44999.00, 20, 'Garmin', 6),

-- Gaming
('PlayStation 5', 'Sony PS5 console', 49999.00, 15, 'Sony', 7),
('Xbox Series X', 'Microsoft Xbox Series X console', 49999.00, 12, 'Microsoft', 7),
('Nintendo Switch OLED', 'Nintendo Switch OLED model', 34999.00, 30, 'Nintendo', 7),
('Steam Deck', 'Portable gaming console', 39999.00, 10, 'Valve', 7),
('Logitech G Pro X', 'Gaming keyboard mechanical', 14999.00, 25, 'Logitech', 7),

-- Cameras
('Canon EOS R6 Mark II', 'Full-frame mirrorless camera', 249999.00, 8, 'Canon', 8),
('Sony A7 IV', 'Full-frame mirrorless camera', 239999.00, 6, 'Sony', 8),
('GoPro Hero 12', 'Action camera with 5.3K video', 39999.00, 20, 'GoPro', 8),
('DJI Mini 4 Pro', 'Compact drone with 4K camera', 84999.00, 12, 'DJI', 8);

-- =========================
-- PRODUCT IMAGES
-- =========================

INSERT INTO product_images (image_url, product_id) VALUES
-- iPhone 15 Pro (id: 1)
('https://images.example.com/iphone15pro-front.jpg', 1),
('https://images.example.com/iphone15pro-back.jpg', 1),
('https://images.example.com/iphone15pro-side.jpg', 1),

-- iPhone 15 (id: 2)
('https://images.example.com/iphone15-front.jpg', 2),
('https://images.example.com/iphone15-back.jpg', 2),

-- Samsung Galaxy S24 Ultra (id: 3)
('https://images.example.com/s24ultra-front.jpg', 3),
('https://images.example.com/s24ultra-back.jpg', 3),
('https://images.example.com/s24ultra-spen.jpg', 3),

-- Samsung Galaxy S24 (id: 4)
('https://images.example.com/s24-front.jpg', 4),
('https://images.example.com/s24-back.jpg', 4),

-- Google Pixel 8 Pro (id: 5)
('https://images.example.com/pixel8pro-front.jpg', 5),
('https://images.example.com/pixel8pro-back.jpg', 5),

-- OnePlus 12 (id: 6)
('https://images.example.com/oneplus12-front.jpg', 6),
('https://images.example.com/oneplus12-back.jpg', 6),

-- MacBook Air M3 (id: 7)
('https://images.example.com/macbookair-m3-front.jpg', 7),
('https://images.example.com/macbookair-m3-angle.jpg', 7),

-- MacBook Pro 14" (id: 8)
('https://images.example.com/macbookpro14-front.jpg', 8),
('https://images.example.com/macbookpro14-side.jpg', 8),

-- Dell XPS 15 (id: 9)
('https://images.example.com/dell-xps15-front.jpg', 9),
('https://images.example.com/dell-xps15-open.jpg', 9),

-- HP Spectre x360 (id: 10)
('https://images.example.com/hp-spectre-front.jpg', 10),
('https://images.example.com/hp-spectre-tent.jpg', 10),

-- Lenovo ThinkPad X1 (id: 11)
('https://images.example.com/thinkpad-x1-front.jpg', 11),

-- ASUS ROG Zephyrus (id: 12)
('https://images.example.com/asus-rog-front.jpg', 12),
('https://images.example.com/asus-rog-keyboard.jpg', 12),

-- iPad Pro 12.9" (id: 13)
('https://images.example.com/ipad-pro-front.jpg', 13),
('https://images.example.com/ipad-pro-pencil.jpg', 13),

-- iPad Air (id: 14)
('https://images.example.com/ipad-air-front.jpg', 14),

-- Samsung Galaxy Tab S9 (id: 15)
('https://images.example.com/tab-s9-front.jpg', 15),
('https://images.example.com/tab-s9-spen.jpg', 15),

-- Microsoft Surface Pro 9 (id: 16)
('https://images.example.com/surface-pro9-front.jpg', 16),

-- Sony WH-1000XM5 (id: 17)
('https://images.example.com/sony-xm5-black.jpg', 17),
('https://images.example.com/sony-xm5-silver.jpg', 17),

-- AirPods Pro 2nd Gen (id: 18)
('https://images.example.com/airpods-pro-case.jpg', 18),
('https://images.example.com/airpods-pro-buds.jpg', 18),

-- Bose QuietComfort Ultra (id: 19)
('https://images.example.com/bose-qc-ultra.jpg', 19),

-- JBL Flip 6 (id: 20)
('https://images.example.com/jbl-flip6-blue.jpg', 20),
('https://images.example.com/jbl-flip6-black.jpg', 20),

-- Sonos One (id: 21)
('https://images.example.com/sonos-one-black.jpg', 21),

-- Logitech MX Master 3S (id: 22)
('https://images.example.com/mx-master-3s.jpg', 22),

-- Apple Magic Keyboard (id: 23)
('https://images.example.com/magic-keyboard.jpg', 23),

-- Anker PowerCore 20000 (id: 24)
('https://images.example.com/anker-powercore.jpg', 24),

-- Belkin USB-C Hub (id: 25)
('https://images.example.com/belkin-hub.jpg', 25),

-- Samsung T7 Shield SSD (id: 26)
('https://images.example.com/t7-shield-blue.jpg', 26),
('https://images.example.com/t7-shield-black.jpg', 26),

-- Apple Watch Series 9 (id: 27)
('https://images.example.com/apple-watch-s9.jpg', 27),

-- Samsung Galaxy Watch 6 (id: 28)
('https://images.example.com/galaxy-watch6.jpg', 28),

-- Fitbit Charge 6 (id: 29)
('https://images.example.com/fitbit-charge6.jpg', 29),

-- Garmin Forerunner 265 (id: 30)
('https://images.example.com/garmin-forerunner265.jpg', 30),

-- PlayStation 5 (id: 31)
('https://images.example.com/ps5-console.jpg', 31),
('https://images.example.com/ps5-controller.jpg', 31),

-- Xbox Series X (id: 32)
('https://images.example.com/xbox-series-x.jpg', 32),

-- Nintendo Switch OLED (id: 33)
('https://images.example.com/switch-oled.jpg', 33),
('https://images.example.com/switch-oled-screen.jpg', 33),

-- Steam Deck (id: 34)
('https://images.example.com/steam-deck.jpg', 34),

-- Logitech G Pro X (id: 35)
('https://images.example.com/logitech-gpro-keyboard.jpg', 35),

-- Canon EOS R6 Mark II (id: 36)
('https://images.example.com/canon-r6-mark2.jpg', 36),

-- Sony A7 IV (id: 37)
('https://images.example.com/sony-a7-iv.jpg', 37),

-- GoPro Hero 12 (id: 38)
('https://images.example.com/gopro-hero12.jpg', 38),

-- DJI Mini 4 Pro (id: 39)
('https://images.example.com/dji-mini4-pro.jpg', 39),
('https://images.example.com/dji-mini4-flying.jpg', 39);

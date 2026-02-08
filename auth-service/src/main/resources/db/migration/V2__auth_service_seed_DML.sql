USE ecommerce;


INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator with full access'),
('USER', 'Regular user with standard access');


-- =========================
-- ADMIN USER
-- =========================
INSERT INTO users (username, password)
VALUES (
    'admin',
    '$2a$10$8S54QnkckZm/U3HujxNYUuxOqCgVI.7R8BA0TjqADLLwhAQUHX/46'
),
('user1','$2a$10$uayYTfTTtNJaPQDHX/u1pO8DwQ7AU/SlMVN8i3jDu5fDWsYOfJr5e')
; -- admin/admin ; user1/user


INSERT INTO user_roles (user_id, role)
VALUES
    (1, 1),
    (2, 2);

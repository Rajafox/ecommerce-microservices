USE ecommerce;

-- =========================
-- ADMIN USER
-- =========================
INSERT INTO users (username, password)
VALUES (
    'admin',
    '$2a$10$7uF6z6R0kM3yYFh3p7YkMe0M0gX6Zx8GQ4z5J2f6cH6ZkZpZyJvD6'
);

SET @admin_id = LAST_INSERT_ID();

INSERT INTO user_roles (user_id, role)
VALUES
    (@admin_id, 'ROLE_ADMIN'),
    (@admin_id, 'ROLE_USER');

-- =========================
-- NORMAL USER
-- =========================
INSERT INTO users (username, password)
VALUES (
    'user',
    '$2a$10$7uF6z6R0kM3yYFh3p7YkMe0M0gX6Zx8GQ4z5J2f6cH6ZkZpZyJvD6'
);

SET @user_id = LAST_INSERT_ID();

INSERT INTO user_roles (user_id, role)
VALUES
    (@user_id, 'ROLE_USER');

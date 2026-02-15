USE ecommerce;

-- =========================
-- ROLES TABLE
-- =========================
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- USERS TABLE
-- =========================
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP

);

-- =========================
-- USER ROLES TABLE
-- =========================
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role
            FOREIGN KEY (role_id)
            REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)

);

-- Optional index for faster role lookups
CREATE INDEX idx_user_roles_user_id
    ON user_roles(user_id);

CREATE TABLE addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address_type VARCHAR(50) NOT NULL DEFAULT 'RESIDENTIAL',
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_addresses_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);
-- Create indexes
CREATE INDEX idx_addresses_user_id ON addresses(user_id);
CREATE INDEX idx_addresses_is_default ON addresses(user_id, is_default);
-- =========================
-- UPDATE USERS TABLE
-- =========================
-- Add phone_number column to users table if not already present
ALTER TABLE users
ADD COLUMN phone_number VARCHAR(17) DEFAULT '091-000-000-0000' NOT NULL;

-- Add first_name and last_name if not already present
ALTER TABLE users
ADD COLUMN  first_name VARCHAR(100) DEFAULT 'User' NOT NULL;
ALTER TABLE users
ADD COLUMN  last_name VARCHAR(100) DEFAULT 'Account' NOT NULL;
ALTER TABLE users
ADD COLUMN IF NOT EXISTS last_name VARCHAR(100) DEFAULT 'Account' NOT NULL;
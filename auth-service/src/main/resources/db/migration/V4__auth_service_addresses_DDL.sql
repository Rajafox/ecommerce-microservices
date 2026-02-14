-- Migration: Create addresses table with granular details
-- Service: auth-service
-- Date: 2026-02-08
USE ecommerce;
-- =========================
-- ADDRESSES TABLE
-- =========================
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
ADD COLUMN IF NOT EXISTS phone_number VARCHAR(15) DEFAULT '+1-000-000-0000' NOT NULL AFTER email;
-- Add timestamps if not already present
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL;
-- Add first_name and last_name if not already present
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS first_name VARCHAR(100) DEFAULT 'User' NOT NULL;
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS last_name VARCHAR(100) DEFAULT 'Account' NOT NULL;

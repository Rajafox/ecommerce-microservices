# 🛒 E-Commerce Microservices Application

A comprehensive, production-ready e-commerce backend system built with **Spring Boot** and microservices architecture. This application provides a complete suite of services for managing online shopping operations including authentication, product catalog, shopping cart, order processing, and payment handling.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Security](#security)
- [References](#references)

## Overview

This project implements a **modular monolithic architecture** organized as a mono-repository, where each service is independently structured but deployed together. It demonstrates real-world backend engineering concepts including:

- ✅ Secure JWT-based authentication and authorization
- ✅ RESTful API design
- ✅ Idempotent payment processing
- ✅ Integration with Stripe payment gateway
- ✅ API documentation with Swagger/OpenAPI

## Features

### Authentication & Authorization
- User registration and login
- JWT token-based authentication
- Password encryption with BCrypt
- Password reset functionality
- User profile management with addresses

### Product Management
- CRUD operations for products
- Category management
- Product image support
- Advanced product search with filters (name, brand, category, price range, stock status)
- Pagination support

### Shopping Cart
- Add/remove items from cart
- Update item quantities
- View cart with calculated totals
- Cart persistence per user
- Automatic cart activation/deactivation on order placement

### Order Management
- Create orders from cart
- Order status tracking (CREATED, PENDING_PAYMENT, PAID, PAYMENT_FAILED)
- Order history for users
- Order details with itemized breakdown

### Payment Processing
- Stripe integration for payment processing
- Idempotent payment handling
- Multi-currency support
- Payment status tracking
- Secure payment confirmation

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     API Gateway / Client                     │
└─────────────────────────────────────────────────────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
┌───────▼────────┐   ┌────────▼────────┐   ┌──────▼──────┐
│  Auth Service  │   │ Product Service │   │ Cart Service│
│   (Port 8081)  │   │   (Port 8082)   │   │ (Port 8083) │
└────────────────┘   └─────────────────┘   └─────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
┌───────▼────────┐   ┌────────▼────────┐   ┌──────▼──────────┐
│ Order Service  │   │ Payment Service │   │ MySQL Database  │
│  (Port 8084)   │   │   (Port 8085)   │   │   (Port 3306)   │
└────────────────┘   └─────────────────┘   └─────────────────┘
        │                     │
        └──────────┬──────────┘
                   │
            ┌──────▼──────┐
            │    Kafka    │
            │ (Port 9092) │
            └─────────────┘
```

### Service Communication
- **Synchronous**: REST APIs for request-response operations


## 🛠️ Technology Stack

### Backend Framework
- **Java 17** - Programming language
- **Spring Boot 3.2.4** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database access layer
- **Spring Web** - RESTful web services

### Database & Persistence
- **MySQL 8.0+** - Relational database
- **Hibernate** - ORM framework


### Payment Gateway
- **Stripe API** - Payment processing

### Security
- **JWT (JSON Web Tokens)** - Token-based authentication


### API Documentation
- **SpringDoc OpenAPI 2.4.0** - Swagger/OpenAPI 3.0 documentation

### Build & Development
- **Maven 3.6+** - Build automation
- **Lombok 1.18.32** - Boilerplate code reduction

## 📁 Project Structure

```
ecommerce-microservices/
│
├── auth-service/                 # Authentication & User Management
│   ├── src/main/
│   │   ├── java/com/ecommerce/auth/
│   │   │   ├── api/              # REST API Contracts
│   │   │   ├── controller/       # REST Controllers
│   │   │   ├── service/          # Business Logic
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── domain/           # Entity Models
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── security/         # JWT & Security Config
│   │   │   └── config/           # Spring Configuration
│   │   └── resources/
│   │       ├── application.yml   # Configuration
│   │       └── db/migration/     # SQL Scripts
│   │           ├── V1__auth_service_DDL.sql
│   │           └── V2__auth_service_seed_DML.sql
│   └── pom.xml
│
├── product-service/              # Product Catalog Management
│   ├── src/main/
│   │   ├── java/com/ecommerce/product/
│   │   │   ├── api/              # REST API Contracts
│   │   │   ├── controller/       # Product & Category Controllers
│   │   │   ├── service/          # Business Logic
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── domain/           # Product, Category, ProductImage Entities
│   │   │   └── dto/              # Request/Response DTOs
│   │   │   ├── security/         # JWT & Security Config
│   │   │   └── config/           # Spring Configuration
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           ├── V1__product_service_DDL.sql
│   │           └── V2__product_service_seed_DML.sql
│   └── pom.xml
│
├── cart-service/                 # Shopping Cart Management
│   ├── src/main/
│   │   ├── java/com/ecommerce/cart/
│   │   │   ├── api/              # REST API Contracts
│   │   │   ├── controller/       # Cart Controller
│   │   │   ├── service/          # Cart Business Logic
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── domain/           # Cart, CartItem Entities
│   │   │   └── dto/              # Request/Response DTOs
│   │   │   ├── security/         # JWT & Security Config
│   │   │   └── config/           # Spring Configuration
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           ├── V1__cart_service_DDL.sql
│   │           └── V2__cart_service_seed_DML.sql
│   └── pom.xml
│
├── order-service/                # Order Processing
│   ├── src/main/
│   │   ├── java/com/ecommerce/order/
│   │   │   ├── api/              # REST API Contracts
│   │   │   ├── controller/       # Order Controller
│   │   │   ├── service/          # Order Business Logic
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── domain/           # Order, OrderItem Entities
│   │   │   ├── dto/              # Request/Response DTOs
│   │   │   ├── security/         # JWT & Security Config
│   │   │   └── config/           # Spring Configuration
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           ├── V1__order_service_DDL.sql
│   │           └── V2__order_service_seed_DML.sql
│   └── pom.xml
│
├── payment-service/              # Payment Processing
│   ├── src/main/
│   │   ├── java/com/ecommerce/payment/
│   │   │   ├── api/              # REST API Contracts
│   │   │   ├── controller/       # Payment Controller
│   │   │   ├── service/          # Payment & Stripe Integration
│   │   │   ├── repository/       # Data Access Layer
│   │   │   ├── domain/           # Payment, Currency Entities
│   │   │   ├── dto/              # Request/Response DTOs
│   │   │   ├── security/         # JWT & Security Config
│   │   │   └── config/           # Spring Configuration
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           ├── V1__payment_service_DDL.sql
│   │           └── V2__payment_service_seed_DML.sql
│   └── pom.xml
│
├── docs/                         # Documentation & Diagrams
│   ├── classDiagram.puml
│   ├── dbDiagram.puml
│   ├── orderPlacement.puml
│   └── deployment.html
│
├── pom.xml                       # Parent POM
├── PROJECT.md                    # Detailed Project Documentation
└── README.md                     # This file
```

##  Database Schema

### Core Tables

#### Auth Service
- **users** - User accounts with credentials
- **roles** - System roles (ADMIN, USER)
- **user_roles** - User-role mapping
- **addresses** - User delivery addresses

#### Product Service
- **products** - Product catalog
- **categories** - Product categories
- **product_images** - Product image URLs

#### Cart Service
- **carts** - Shopping carts
- **cart_items** - Items in cart with quantities

#### Order Service
- **orders** - Order records
- **order_items** - Items in each order

#### Payment Service
- **payments** - Payment transactions
- **currencies** - Supported currencies

### Database Relationships
- User → Orders (One-to-Many)
- User → Addresses (One-to-Many)
- Order → Order Items (One-to-Many)
- Order → Payment (One-to-One)
- Cart → Cart Items (One-to-Many)
- Product → Category (Many-to-One)
- Product → Product Images (One-to-Many)

##  Prerequisites

Before running the application, ensure you have the following installed:

### Required Software
- **Java Development Kit (JDK) 17** or higher
  - Download: https://adoptium.net/
  - Verify: `java -version`

- **Maven 3.6+**
  - Download: https://maven.apache.org/download.cgi
  - Verify: `mvn -version`

- **MySQL 8.0+**
  - Download: https://dev.mysql.com/downloads/mysql/
  - Verify: `mysql --version`

### Optional Tools
- **Postman** or **Insomnia** - API testing
- **MySQL Workbench** - Database management
- **IntelliJ IDEA** or **Eclipse** - IDE

##  Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ecommerce-microservices
```

### 2. Configure MySQL Database

Create the database and user:

```sql
-- Create database
CREATE DATABASE ecommerce;

-- Create user and grant privileges
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON ecommerce.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Update Application Configuration

Update the database credentials in each service's `application.yml`:

**Location**: `<service-name>/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce
    username: ecommerce_user  # Update this
    password: your_password    # Update this
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Update for:
- `auth-service/src/main/resources/application.yml`
- `product-service/src/main/resources/application.yml`
- `cart-service/src/main/resources/application.yml`
- `order-service/src/main/resources/application.yml`
- `payment-service/src/main/resources/application.yml`

### 4. Configure Stripe (Payment Service)

Get your Stripe API keys:
1. Sign up at https://stripe.com
2. Go to Developers → API Keys
3. Copy your **Secret Key**

Update `payment-service/src/main/resources/application.yml`:

```yaml
stripe:
  secret:
    key: sk_test_YOUR_STRIPE_SECRET_KEY
```

### 5. Configure JWT Secret

Update the JWT secret in all services (same value for all):

```yaml
jwt:
  secret: your-secure-secret-key-at-least-256-bits-long
  expiration: 1800000  # 30 minutes
```

### 7. Build the Project

Build all services from the root directory:

```bash
mvn clean install
```

This will:
- Compile all services
- Run tests
- Create JAR files in each service's `target/` directory

## 🚀 Running the Application

### Run Database Scripts

Run DB scripts for services in order : Auth Service, Product Service, Cart Service, Order Service and Payment Services. Scripts are in following format:

1. **V1__*_DDL.sql** - Creates database tables (DDL - Data Definition Language)
2. **V2__*_seed_DML.sql** - Inserts seed data (DML - Data Manipulation Language)

### Start Services

Start each service in **order** (to ensure dependencies are available):

#### 1. Start Auth Service (Port 8081)
```bash
cd auth-service
mvn spring-boot:run
```

#### 2. Start Product Service (Port 8082)
```bash
cd product-service
mvn spring-boot:run
```

#### 3. Start Cart Service (Port 8083)
```bash
cd cart-service
mvn spring-boot:run
```

#### 4. Start Payment Service (Port 8085)
```bash
cd payment-service
mvn spring-boot:run
```

#### 5. Start Order Service (Port 8084)
```bash
cd order-service
mvn spring-boot:run
```

### Alternative: Run All Services with JAR Files

After building with `mvn clean install`:

```bash
# Terminal 1 - Auth Service
java -jar auth-service/target/auth-service-1.0.0-SNAPSHOT.jar

# Terminal 2 - Product Service
java -jar product-service/target/product-service-1.0.0-SNAPSHOT.jar

# Terminal 3 - Cart Service
java -jar cart-service/target/cart-service-1.0.0-SNAPSHOT.jar

# Terminal 4 - Payment Service
java -jar payment-service/target/payment-service-1.0.0-SNAPSHOT.jar

# Terminal 5 - Order Service
java -jar order-service/target/order-service-1.0.0-SNAPSHOT.jar
```

## 📚 API Documentation

### Swagger UI

Each service has its own Swagger UI documentation:

- **Auth Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8082/swagger-ui.html
- **Cart Service**: http://localhost:8083/swagger-ui.html
- **Order Service**: http://localhost:8084/swagger-ui.html
- **Payment Service**: http://localhost:8085/swagger-ui.html

### API Endpoints

#### Authentication Service (Port 8081)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/users` | Register new user | No |
| POST | `/auth/login` | User login | No |
| GET | `/auth/me` | Get current user info | Yes |
| PUT | `/auth/users/{id}` | Update user profile | Yes |
| POST | `/auth/reset-password` | Reset password | Yes |

#### Product Service (Port 8082)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/products` | Get all products | No |
| GET | `/products/{id}` | Get product by ID | No |
| GET | `/products/search` | Search products with filters | No |
| POST | `/products` | Create product (ADMIN) | Yes |
| PUT | `/products/{id}` | Update product (ADMIN) | Yes |
| DELETE | `/products/{id}` | Delete product (ADMIN) | Yes |
| GET | `/categories` | Get all categories | No |
| POST | `/categories` | Create category (ADMIN) | Yes |
| PUT | `/categories/{id}` | Update category (ADMIN) | Yes |
| DELETE | `/categories/{id}` | Delete category (ADMIN) | Yes |

#### Cart Service (Port 8083)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/cart` | Get current user's cart | Yes |
| POST | `/cart/items` | Add item to cart | Yes |
| DELETE | `/cart/items/{productId}` | Remove item from cart | Yes |
| DELETE | `/cart` | Clear cart | Yes |

#### Order Service (Port 8084)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/orders` | Create order from cart | Yes |
| GET | `/orders` | Get all user orders | Yes |
| GET | `/orders/{id}` | Get order details | Yes |

#### Payment Service (Port 8085)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/payments` | Process payment | Yes |

### Example API Workflow

#### 1. Register a User
```bash
curl -X POST http://localhost:8081/auth/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!",
    "roles": ["USER"],
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com"
  }'
```

#### 2. Login
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!"
  }'
```

Response:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### 3. Browse Products
```bash
curl http://localhost:8082/products
```

#### 4. Search Products
```bash
curl "http://localhost:8082/products/search?name=iPhone&minPrice=50000&maxPrice=150000&page=0&size=10"
```

#### 5. Add to Cart
```bash
curl -X POST http://localhost:8083/cart/items \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

#### 6. View Cart
```bash
curl http://localhost:8083/cart \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 7. Create Order
```bash
curl -X POST http://localhost:8084/orders \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

#### 8. Process Payment
```bash
curl -X POST http://localhost:8085/payments \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 1,
    "amount": 159998.00,
    "currency": "INR"
  }'
```

### Default Test Users

After seed data migration:

| Username | Password | Role |
|----------|----------|------|
| admin | admin | ADMIN |
| user1 | user | USER |

## 🧪 Testing

### Manual Testing

1. **Use Swagger UI**: Navigate to each service's Swagger UI endpoint
2. **Use Postman**: Import API endpoints and test manually
3. **Use curl**: Run command-line tests as shown above

### Test Data

The application includes comprehensive seed data:
- **8 Categories** (Smartphones, Laptops, Tablets, Audio, etc.)
- **39 Products** across all categories
- **65+ Product Images**
- **4 Carts** (2 active, 2 inactive)
- **13 Cart Items**
- **7 Orders** with various statuses
- **6 Payments** (succeeded, pending, failed)
- **12 Currencies** including INR
- **2 Users** (admin, user1)
- **2 Roles** (ADMIN, USER)

### Integration Testing

Test the complete flow:
1. Register → Login → Get JWT
2. Browse Products → Search
3. Add to Cart → View Cart
4. Place Order → Verify Order Status
5. Process Payment → Confirm Payment

## Security

### Authentication Flow

1. User sends credentials to `/auth/login`
2. Server validates credentials using BCrypt
3. JWT token is generated and returned
4. Client includes token in `Authorization` header for protected endpoints
   ```
   Authorization: Bearer <JWT_TOKEN>
   ```

### Password Security
- Passwords are hashed using **BCrypt** with salt
- Plain text passwords are never stored
- Password strength validation on registration

### JWT Configuration

```yaml
jwt:
  secret: a-string-secret-at-least-256-bits-long
  expiration: 1800000  # 30 minutes (in milliseconds)
```

### Security Best Practices Implemented

✅ JWT token-based stateless authentication  
✅ BCrypt password hashing with salt  
✅ Role-based authorization with Spring Security  
✅ CORS configuration for cross-origin requests  
✅ Input validation with Bean Validation  
✅ SQL injection prevention with JPA/Hibernate  
✅ Idempotent payment processing  
✅ Secure Stripe API key management  

## 🎓 References

### Technologies & Frameworks

1. **Spring Boot**
   - Official Documentation: https://spring.io/projects/spring-boot
   - Reference Guide: https://docs.spring.io/spring-boot/docs/current/reference/html/

2. **Spring Security**
   - Documentation: https://spring.io/projects/spring-security
   - JWT Implementation: https://jwt.io/

3. **Spring Data JPA**
   - Documentation: https://spring.io/projects/spring-data-jpa
   - Hibernate ORM: https://hibernate.org/

6. **Stripe Payment Gateway**
   - API Documentation: https://stripe.com/docs/api
   - Java Library: https://github.com/stripe/stripe-java

7. **OpenAPI/Swagger**
   - SpringDoc: https://springdoc.org/
   - OpenAPI Specification: https://swagger.io/specification/

### Design Patterns & Architecture

8. **Microservices Architecture**
   - Martin Fowler: https://martinfowler.com/articles/microservices.html
   - Microservices Patterns: https://microservices.io/patterns/

9. **RESTful API Design**
   - REST API Best Practices: https://restfulapi.net/
   - Roy Fielding's Dissertation: https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm

10. **Database Design**
    - Database Normalization: https://www.geeksforgeeks.org/database-normalization-normal-forms/
    - MySQL Documentation: https://dev.mysql.com/doc/

### Security

11. **JSON Web Tokens (JWT)**
    - JWT.io: https://jwt.io/introduction
    - JJWT Library: https://github.com/jwtk/jjwt

12. **BCrypt Password Hashing**
    - BCrypt Algorithm: https://en.wikipedia.org/wiki/Bcrypt
    - Spring Security BCrypt: https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html

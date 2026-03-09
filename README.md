# Burger POS Backend

![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8%2B-orange?logo=mysql)
![Maven](https://img.shields.io/badge/Maven-Wrapper-red?logo=apachemaven)

A **RESTful backend API** built with Spring Boot for a **Burger Restaurant Point of Sale (POS) system**. It provides full menu management (products) and order processing, with automatic database schema management via Hibernate. Designed to integrate seamlessly with an Angular frontend.

## Features

- 🍔 **Product (Menu) Management** — Create, read, update, and delete burger menu items with name, category, price, and image URL.
- 📋 **Order Processing** — Place customer orders containing multiple items; the system automatically calculates totals and snapshots prices at order time.
- 🗄️ **Auto Schema Management** — Database tables are created and updated automatically by Hibernate on startup.
- 🔗 **CORS Support** — Pre-configured to accept requests from an Angular dev server (`http://localhost:4200`).
- 🔒 **Security-Ready** — Spring Security dependency is included and can be enabled when needed.

## Tech Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 4.0.2 |
| Spring Data JPA / Hibernate | (managed by Spring Boot) |
| MySQL | 8+ |
| Lombok | (managed by Spring Boot) |
| ModelMapper | 3.2.0 |
| Maven | Wrapper included |

## Project Structure

```
burger/
└── src/
    └── main/
        ├── java/com/example/burger/
        │   ├── BurgerApplication.java          # Application entry point
        │   ├── config/
        │   │   └── ModelMapperConfig.java       # ModelMapper bean configuration
        │   ├── controller/
        │   │   ├── ProductController.java       # Product CRUD endpoints
        │   │   └── OrderController.java         # Order placement endpoint
        │   ├── model/
        │   │   ├── dto/                         # Data Transfer Objects
        │   │   │   ├── ProductDto.java
        │   │   │   ├── OrderRequestDto.java
        │   │   │   ├── OrderResponseDto.java
        │   │   │   ├── OrderItemRequestDto.java
        │   │   │   └── OrderItemResponseDto.java
        │   │   └── entity/                      # JPA Entities
        │   │       ├── Product.java
        │   │       ├── Order.java
        │   │       └── OrderItem.java
        │   ├── repository/
        │   │   ├── ProductRepository.java
        │   │   └── OrderRepository.java
        │   └── service/
        │       ├── ProductService.java
        │       └── OrderService.java
        └── resources/
            └── application.yaml                 # App configuration
```

## Prerequisites

- **Java 21** or later
- **MySQL 8+** running on `localhost:3306`
- **Maven** (or use the included `mvnw` wrapper)

## Getting Started

### 1. Configure the Database

The application connects to MySQL using the settings in `burger/src/main/resources/application.yaml`. Update the credentials to match your local MySQL setup:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/burger_pos?createDatabaseIfNotExist=true
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  application:
    name: burger
```

> The `createDatabaseIfNotExist=true` parameter will automatically create the `burger_pos` database on first run. The `ddl-auto: update` setting will auto-create and update tables based on the JPA entities.

### 2. Build the Project

```bash
cd burger

# Linux / macOS
./mvnw clean install

# Windows
mvnw.cmd clean install
```

### 3. Run the Application

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

### 4. Run Tests

```bash
# Linux / macOS
./mvnw test

# Windows
mvnw.cmd test
```

## API Endpoints

### Products

Base URL: `/products`

| Method | Endpoint | Description | Request Body | Response |
|---|---|---|---|---|
| `GET` | `/products/all` | Get all products | — | `List<ProductDto>` |
| `GET` | `/products/get/{id}` | Get a product by ID | — | `ProductDto` |
| `POST` | `/products/add` | Create a new product | `ProductDto` | `ProductDto` (201 Created) |
| `PUT` | `/products/{id}` | Update an existing product | `ProductDto` | `ProductDto` |
| `DELETE` | `/products/{id}` | Delete a product | — | 204 No Content |

**ProductDto example:**
```json
{
  "id": 1,
  "name": "Classic Burger",
  "category": "Burgers",
  "price": 8.99,
  "imageUrl": "https://example.com/images/classic-burger.jpg"
}
```

---

### Orders

Base URL: `/order`

| Method | Endpoint | Description | Request Body | Response |
|---|---|---|---|---|
| `POST` | `/order/place` | Place a new order | `OrderRequestDto` | `OrderResponseDto` |

**OrderRequestDto example:**
```json
{
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

**OrderResponseDto example:**
```json
{
  "id": 101,
  "createdAt": "2025-01-01T12:00:00",
  "total": 26.97,
  "status": "PENDING",
  "items": [
    { "productId": 1, "quantity": 2, "price": 8.99 },
    { "productId": 3, "quantity": 1, "price": 8.99 }
  ]
}
```

## Database Schema

The schema is managed automatically by Hibernate (`ddl-auto: update`).

### `product`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated primary key |
| `name` | VARCHAR | Product name |
| `category` | VARCHAR | Product category (e.g. "Burgers", "Drinks") |
| `price` | DOUBLE | Product price |
| `image_url` | VARCHAR | URL to the product image |

### `order`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated primary key |
| `created_at` | DATETIME | Timestamp of when the order was placed |
| `total` | DOUBLE | Total cost of the order |
| `status` | VARCHAR | Order status (default: `PENDING`) |

### `order_item`
| Column | Type | Description |
|---|---|---|
| `id` | BIGINT (PK) | Auto-generated primary key |
| `order_id` | BIGINT (FK) | Reference to the parent order |
| `product_id` | BIGINT (FK) | Reference to the product |
| `quantity` | INT | Number of units ordered |
| `price` | DOUBLE | Price snapshot at the time of ordering |

## CORS Configuration

The API is configured to accept requests from `http://localhost:4200` (default Angular dev server). To change this, update the `@CrossOrigin` annotation in `ProductController.java` and `OrderController.java`.

## Notes

- **Authentication** is not currently enabled. The `spring-boot-starter-security` dependency is present in the project but commented out in `pom.xml`.
- SQL queries are logged to the console (`show-sql: true`). This can be disabled in `application.yaml` for production.

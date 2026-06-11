# E-Commerce Microservices

This project is an e-commerce application that utilizes microservices architecture in order to create, manage, and store orders. It works with MySQL to create and store databases. When an order is placed, a synchronous request is made to the inventory service to check if the item is in stock. If it is, the order is saved with a pending status and a notification is sent through Kafka.

I built this as microservices so I could get hands-on experience with microservice communication in an application architecture. The hardest part was making the HTTP connection between the different services and allowing for fluid connection.

## Services

| Service | Port | Description |
|---|---|---|
| Order Service | 8081 | Places orders and checks inventory through HTTP |
| Inventory Service | 8082 | Manages product stock |
| Notification Service | 8083 | Listens to Kafka events and logs notifications |

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- MySQL
- Apache Kafka (Docker)
- RestTemplate for synchronous requests
- Docker

## How It Works

1. POST /api/orders with a product name
2. Order Service makes a synchronous request to Inventory Service to check stock
3. If in stock, order is saved with PENDING status
4. Order Service publishes an event to the Kafka order-topic
5. Notification Service is a listener of this topic and logs the message

## Running the Project

### Prerequisites
- Java 17
- MySQL
- Maven
- Docker (for Kafka)

### Setup

1. Start Kafka
```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:3.7.0
```

2. Create the databases
```sql
CREATE DATABASE order_db;
CREATE DATABASE inventory_db;
```

3. Start each service:
```bash
cd inventory-service && ./mvnw spring-boot:run
cd order-service && ./mvnw spring-boot:run
cd notification-service && ./mvnw spring-boot:run
```

## Example Request

**Place an order**
```json
POST /api/orders
{
  "productName": "Laptop",
  "quantity": 1,
  "price": 999.99
}
```

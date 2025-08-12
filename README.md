# RetailX Inventory API

A Spring Boot 3 REST API for managing product inventory in the RetailX ecosystem.

## Overview

This service provides inventory management capabilities including stock level tracking, inventory updates, and integration with other RetailX services.

## Features

- **GET /inventory/{productId}** - Retrieve current stock information for a product
- **PUT /inventory/{productId}** - Update stock levels for a product
- **Health Check** - Service health monitoring endpoint
- **OpenAPI Documentation** - Interactive API documentation via Swagger UI

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Maven** - Build tool
- **OpenAPI 3** - API documentation
- **Docker** - Containerization

## Dependencies

### Core Dependencies
- `spring-boot-starter-web` - Web framework and REST API support
- `spring-boot-starter-validation` - Request validation
- `springdoc-openapi-starter-webmvc-ui` - OpenAPI/Swagger documentation

### External Service Integration
- **retailx-orders-api** (http://localhost:8082) - Order management service integration

## Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (or use included Maven wrapper)
- Docker (optional, for containerized deployment)

### Running Locally

1. **Clone and navigate to the project:**
   ```bash
   cd retailx-inventory-api
   ```

2. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Access the API:**
   - Base URL: http://localhost:8083
   - Health Check: http://localhost:8083/inventory/health
   - API Documentation: http://localhost:8083/swagger-ui.html

### Docker Deployment

1. **Build Docker image:**
   ```bash
   docker build -t retailx-inventory-api .
   ```

2. **Run container:**
   ```bash
   docker run -p 8083:8083 retailx-inventory-api
   ```

## API Endpoints

### Get Inventory
```http
GET /inventory/{productId}
```

**Response Example:**
```json
{
  "productId": "PROD-001",
  "stockLevel": 50,
  "available": true,
  "location": "WAREHOUSE-A",
  "status": "AVAILABLE"
}
```

### Update Inventory
```http
PUT /inventory/{productId}
```

**Request Body:**
```json
{
  "stockLevel": 75,
  "location": "WAREHOUSE-A",
  "operation": "SET"
}
```

**Operations:**
- `SET` - Set stock to exact value
- `ADD` - Add to current stock
- `SUBTRACT` - Subtract from current stock

## Service Integration

### Orders API Integration
When inventory is updated, this service automatically notifies the Orders API about stock changes to enable:
- Order fulfillment updates
- Backorder processing
- Inventory reservation management

## Configuration

Key configuration properties in `application.properties`:

```properties
server.port=8083
spring.application.name=retailx-inventory-api
retailx.orders.api.url=http://localhost:8082
```

## Development TODOs

### High Priority
- [ ] **Database Integration** - Replace in-memory storage with persistent database (PostgreSQL/MySQL)
- [ ] **Authentication & Authorization** - Implement security for production use
- [ ] **Request Validation** - Add comprehensive input validation and error handling
- [ ] **Actual HTTP Client** - Implement real HTTP calls to Orders API using RestTemplate/WebClient

### Medium Priority
- [ ] **Transaction Support** - Add database transactions for stock updates
- [ ] **Inventory Reservation** - Implement stock reservation for pending orders
- [ ] **Async Processing** - Add asynchronous processing for external service calls
- [ ] **Retry Logic** - Implement circuit breaker pattern for external service calls
- [ ] **Monitoring & Metrics** - Add application metrics and monitoring endpoints

### Low Priority
- [ ] **Multi-warehouse Support** - Enhanced location-based inventory management
- [ ] **Inventory Alerts** - Low stock level notifications
- [ ] **Batch Operations** - Support for bulk inventory updates
- [ ] **Audit Trail** - Track all inventory changes with timestamps and reasons
- [ ] **Rate Limiting** - Implement API rate limiting for production
- [ ] **Caching** - Add Redis caching for frequently accessed inventory data

## Testing

### Manual Testing with curl

**Get inventory:**
```bash
curl http://localhost:8083/inventory/PROD-001
```

**Update inventory:**
```bash
curl -X PUT http://localhost:8083/inventory/PROD-001 \
  -H "Content-Type: application/json" \
  -d '{"stockLevel": 100, "operation": "SET", "location": "WAREHOUSE-A"}'
```

### Sample Products
The service comes pre-loaded with sample data:
- `PROD-001` - 50 units in WAREHOUSE-A
- `PROD-002` - 25 units in WAREHOUSE-B  
- `PROD-003` - 0 units (out of stock)
- `PROD-004` - 100 units in WAREHOUSE-C

## Architecture Notes

This service is part of the RetailX microservices ecosystem:

- **foo-legacy-notifications-api** (port 8081) - Legacy notification service
- **retailx-orders-api** (port 8082) - Order management service  
- **retailx-inventory-api** (port 8083) - This inventory service

The service demonstrates enterprise patterns including:
- Service-to-service communication
- OpenAPI documentation
- Docker containerization
- Structured logging
- Health check endpoints

## Contributing

When adding new features:
1. Update the TODO lists in this README
2. Add appropriate TODO comments in code
3. Maintain OpenAPI documentation
4. Follow existing code patterns and logging practices

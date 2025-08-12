package com.retailx.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for RetailX Inventory API
 * 
 * TODO: Add configuration for database connections
 * TODO: Add security configuration for production
 * TODO: Add monitoring and health check endpoints
 */
@SpringBootApplication
public class InventoryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApiApplication.class, args);
    }
}

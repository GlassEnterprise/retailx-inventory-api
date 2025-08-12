package com.retailx.inventory.service;

import com.retailx.inventory.client.OrdersApiClient;
import com.retailx.inventory.model.InventoryResponse;
import com.retailx.inventory.model.UpdateInventoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class for managing inventory operations
 * 
 * TODO: Replace in-memory storage with database persistence
 * TODO: Add transaction support for stock updates
 * TODO: Implement inventory reservation logic
 * TODO: Add support for multiple warehouse locations
 * TODO: Add inventory alerts for low stock levels
 */
@Service
public class InventoryService {
    
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);
    
    @Autowired
    private OrdersApiClient ordersApiClient;
    
    // TODO: Replace with database storage
    private final Map<String, InventoryData> inventoryStore = new HashMap<>();
    
    // Initialize with some mock data
    {
        inventoryStore.put("PROD-001", new InventoryData("PROD-001", 50, "WAREHOUSE-A", "ACTIVE"));
        inventoryStore.put("PROD-002", new InventoryData("PROD-002", 25, "WAREHOUSE-B", "ACTIVE"));
        inventoryStore.put("PROD-003", new InventoryData("PROD-003", 0, "WAREHOUSE-A", "OUT_OF_STOCK"));
        inventoryStore.put("PROD-004", new InventoryData("PROD-004", 100, "WAREHOUSE-C", "ACTIVE"));
    }
    
    /**
     * Get inventory information for a specific product
     */
    public InventoryResponse getInventory(String productId) {
        logger.info("Retrieving inventory for product: {}", productId);
        
        InventoryData data = inventoryStore.get(productId);
        if (data == null) {
            // TODO: Throw custom ProductNotFoundException
            logger.warn("Product not found: {}", productId);
            return new InventoryResponse(productId, 0, false, "UNKNOWN", "NOT_FOUND");
        }
        
        boolean available = data.stockLevel > 0;
        String status = available ? "AVAILABLE" : "OUT_OF_STOCK";
        
        logger.info("Retrieved inventory for product {}: stock={}, available={}", 
                   productId, data.stockLevel, available);
        
        return new InventoryResponse(productId, data.stockLevel, available, data.location, status);
    }
    
    /**
     * Update inventory stock level for a product
     */
    public InventoryResponse updateInventory(String productId, UpdateInventoryRequest request) {
        logger.info("Updating inventory for product: {} with operation: {}", productId, request.getOperation());
        
        InventoryData data = inventoryStore.get(productId);
        if (data == null) {
            // Create new product entry
            data = new InventoryData(productId, 0, 
                                   request.getLocation() != null ? request.getLocation() : "WAREHOUSE-A", 
                                   "ACTIVE");
            inventoryStore.put(productId, data);
            logger.info("Created new product entry for: {}", productId);
        }
        
        int oldStockLevel = data.stockLevel;
        int newStockLevel = calculateNewStockLevel(data.stockLevel, request);
        
        // TODO: Add validation for negative stock levels
        data.stockLevel = Math.max(0, newStockLevel);
        
        if (request.getLocation() != null) {
            data.location = request.getLocation();
        }
        
        // Update status based on stock level
        data.status = data.stockLevel > 0 ? "ACTIVE" : "OUT_OF_STOCK";
        
        logger.info("Updated inventory for product {}: {} -> {} (operation: {})", 
                   productId, oldStockLevel, data.stockLevel, request.getOperation());
        
        // TODO: Add async processing for better performance
        // Notify orders API about the inventory change
        try {
            ordersApiClient.notifyInventoryChange(productId, data.stockLevel, request.getOperation());
        } catch (Exception e) {
            // TODO: Add proper error handling and retry logic
            logger.error("Failed to notify Orders API about inventory change for product {}: {}", 
                        productId, e.getMessage());
        }
        
        boolean available = data.stockLevel > 0;
        String status = available ? "AVAILABLE" : "OUT_OF_STOCK";
        
        return new InventoryResponse(productId, data.stockLevel, available, data.location, status);
    }
    
    private int calculateNewStockLevel(int currentLevel, UpdateInventoryRequest request) {
        String operation = request.getOperation() != null ? request.getOperation().toUpperCase() : "SET";
        
        switch (operation) {
            case "ADD":
                return currentLevel + request.getStockLevel();
            case "SUBTRACT":
                return currentLevel - request.getStockLevel();
            case "SET":
            default:
                return request.getStockLevel();
        }
    }
    
    // TODO: Move to separate entity class when adding database support
    private static class InventoryData {
        String productId;
        int stockLevel;
        String location;
        String status;
        
        InventoryData(String productId, int stockLevel, String location, String status) {
            this.productId = productId;
            this.stockLevel = stockLevel;
            this.location = location;
            this.status = status;
        }
    }
}

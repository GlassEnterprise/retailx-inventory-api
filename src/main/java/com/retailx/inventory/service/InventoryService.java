package com.retailx.inventory.service;

import com.retailx.inventory.client.OrdersApiClient;
import com.retailx.inventory.entity.InventoryItem;
import com.retailx.inventory.mappers.ProductMapper;
import com.retailx.inventory.model.Product;
import com.retailx.inventory.model.UpdateInventoryRequest;
import com.retailx.inventory.repository.InventoryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private OrdersApiClient ordersApiClient;
    
    public Product getInventory() {
        return inventoryItemRepository.findAll().stream()
                .map(productMapper::toDto)
                .findFirst()
                .orElse(null);
    }
    
    
    public Product getInventory(int productId) {
        logger.info("Retrieving inventory for product: {}", productId);
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findByProductId(productId);
        return inventoryItemOptional.map(productMapper::toDto)
                .orElse(null);
    }
    
    public void updateInventory(UpdateInventoryRequest request) {
        logger.info("Updating inventory for product: {}", request.getProductId());
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findByProductId(request.getProductId());
        InventoryItem inventoryItem = inventoryItemOptional.orElse(new InventoryItem());

        inventoryItem.setProductId(request.getProductId());
        inventoryItem.setQuantity(request.getQuantity());
        inventoryItem.setStatus(request.getStatus());
        // In a real scenario, product name would be fetched from a product service
        inventoryItem.setProductName("Sample Product");

        inventoryItemRepository.save(inventoryItem);
        logger.info("Inventory updated for product: {}", request.getProductId());
    }
}

package com.retailx.inventory.controller;

import com.retailx.inventory.model.Product;
import com.retailx.inventory.model.UpdateInventoryRequest;
import com.retailx.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for inventory management operations
 * 
 * TODO: Add request validation and error handling
 * TODO: Add rate limiting for production use
 * TODO: Add authentication and authorization
 * TODO: Add metrics and monitoring endpoints
 */
@RestController
@RequestMapping("/inventory")
@Tag(name = "Inventory Management", description = "APIs for managing product inventory")
public class InventoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
    
    @Autowired
    private InventoryService inventoryService;
    
    /**
     * Get inventory information for a specific product
     */
    @GetMapping("/{productId}")
    @Operation(summary = "Get product inventory", description = "Retrieve current stock information for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory information"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Product> getInventory(
            @Parameter(description = "Product ID to retrieve inventory for", required = true)
            @PathVariable int productId) {
        
        logger.info("GET /inventory/{} - Retrieving inventory information", productId);
        
        try {
            Product product = inventoryService.getInventory(productId);
            
            if (product == null) {
                logger.warn("Product not found: {}", productId);
                return ResponseEntity.notFound().build();
            }
            
            logger.info("Successfully retrieved inventory for product: {}", productId);
            return ResponseEntity.ok(product);
            
        } catch (Exception e) {
            logger.error("Error retrieving inventory for product {}: {}", productId, e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/")
    @Operation(summary = "Get product inventory", description = "Retrieve current stock information for a specific product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved inventory information"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Product> getInventory(
            ) {
        
        
        try {
            Product product = inventoryService.getInventory();
            
            if (product == null) {
                logger.warn("Product not found:");
                return ResponseEntity.notFound().build();
            }
            
            logger.info("Successfully retrieved inventory for products");
            return ResponseEntity.ok(product);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PostMapping("/update")
    @Operation(summary = "Update product inventory", description = "Update stock level for a specific product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated inventory"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updateInventory(@RequestBody UpdateInventoryRequest request) {
        logger.info("POST /inventory/update - Updating inventory for product: {}", request.getProductId());
        try {
            inventoryService.updateInventory(request);
            logger.info("Successfully updated inventory for product: {}", request.getProductId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating inventory for product {}: {}", request.getProductId(), e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    /**
     * Health check endpoint
     * TODO: Move to separate health controller
     */
    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Check if the inventory service is healthy")
    public ResponseEntity<String> healthCheck() {
        logger.debug("Health check requested");
        return ResponseEntity.ok("Inventory API is healthy");
    }
}

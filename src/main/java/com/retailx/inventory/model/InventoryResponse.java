package com.retailx.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response model for inventory information
 * 
 * TODO: Add validation annotations for production use
 * TODO: Consider adding product name and description fields
 * TODO: Add timestamp for last stock update
 */
public class InventoryResponse {
    
    @JsonProperty("productId")
    private String productId;
    
    @JsonProperty("stockLevel")
    private Integer stockLevel;
    
    @JsonProperty("available")
    private Boolean available;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("status")
    private String status;

    public InventoryResponse() {}

    public InventoryResponse(String productId, Integer stockLevel, Boolean available, String location, String status) {
        this.productId = productId;
        this.stockLevel = stockLevel;
        this.available = available;
        this.location = location;
        this.status = status;
    }

    // Getters and setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

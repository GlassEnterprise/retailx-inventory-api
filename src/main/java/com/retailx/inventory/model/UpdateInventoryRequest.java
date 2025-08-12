package com.retailx.inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request model for updating inventory stock levels
 * 
 * TODO: Add validation annotations (@NotNull, @Min, etc.)
 * TODO: Add support for batch updates
 * TODO: Consider adding reason field for audit trail
 */
public class UpdateInventoryRequest {
    
    @JsonProperty("stockLevel")
    private Integer stockLevel;
    
    @JsonProperty("location")
    private String location;
    
    @JsonProperty("operation")
    private String operation; // "SET", "ADD", "SUBTRACT"

    public UpdateInventoryRequest() {}

    public UpdateInventoryRequest(Integer stockLevel, String location, String operation) {
        this.stockLevel = stockLevel;
        this.location = location;
        this.operation = operation;
    }

    // Getters and setters
    public Integer getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {
        this.stockLevel = stockLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}

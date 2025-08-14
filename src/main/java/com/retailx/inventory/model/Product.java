package com.retailx.inventory.model;

public class Product {
    private int productId;
    private String name;
    private int quantity;
    private String status;

    public Product(int productId, String name, int quantity, String status) {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.status = status;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

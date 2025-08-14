package com.retailx.inventory.mappers;

import com.retailx.inventory.entity.InventoryItem;
import com.retailx.inventory.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDto(InventoryItem inventoryItem) {
        return new Product(
                inventoryItem.getProductId(),
                inventoryItem.getProductName(),
                inventoryItem.getQuantity(),
                inventoryItem.getStatus()
        );
    }

    public InventoryItem toEntity(Product product) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setProductId(product.getProductId());
        inventoryItem.setProductName(product.getName());
        inventoryItem.setQuantity(product.getQuantity());
        inventoryItem.setStatus(product.getStatus());
        return inventoryItem;
    }
}

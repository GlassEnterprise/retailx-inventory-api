package com.retailx.inventory.repository;

import com.retailx.inventory.entity.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {
    Optional<InventoryItem> findByProductId(int productId);
}

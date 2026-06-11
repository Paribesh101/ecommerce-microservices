package com.ecommerce.inventoryservice.service;

import org.springframework.stereotype.Service;

import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public Inventory addInventory(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    public boolean isInStock(String productName){
        return inventoryRepository.findByProductName(productName)
                .map(inventory -> inventory.getQuantity() > 0)
                .orElse(false);
    }

}
package com.ecommerce.inventoryservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventoryservice.model.Inventory;
import com.ecommerce.inventoryservice.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController{

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> addInventory(@RequestBody Inventory inventory){
        Inventory created = inventoryService.addInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<Boolean> isInStock(@PathVariable String productName){
        boolean inStock = inventoryService.isInStock(productName);
        return ResponseEntity.ok(inStock);
    }

    }


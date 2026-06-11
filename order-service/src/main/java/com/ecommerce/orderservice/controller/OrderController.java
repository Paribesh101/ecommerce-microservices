package com.ecommerce.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody Order order){
        Order created = orderService.placeOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    

}


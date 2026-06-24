package com.ecommerce.orderservice.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.orderservice.exception.OutOfStockException;
import com.ecommerce.orderservice.kafka.OrderProducer;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final OrderProducer orderProducer;

    @Value("${inventory.service.url:http://localhost:8082}")
    private String inventoryServiceUrl;

    public Order placeOrder(Order order) {
        boolean inStock = restTemplate.getForObject(
            inventoryServiceUrl + "/api/inventory/" + order.getProductName(),
            Boolean.class
        );
        if (!inStock) {
            throw new OutOfStockException("Product is out of stock");
        }
        order.setOrderStatus("PENDING");
        Order savedOrder = orderRepository.save(order);
        orderProducer.sendOrderEvent("Order placed for: " + order.getProductName());
        return savedOrder;
    }
}
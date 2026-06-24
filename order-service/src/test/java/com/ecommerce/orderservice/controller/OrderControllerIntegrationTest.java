package com.ecommerce.orderservice.controller;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    void placeOrder_returnsCreatedStatus() throws Exception {
        // Arrange
        Order order = new Order();
        order.setProductName("Laptop");
        order.setOrderStatus("PENDING");

        when(orderService.placeOrder(any(Order.class))).thenReturn(order);

        // Act + Assert
        mockMvc.perform(post("/api/orders")
               .contentType(MediaType.APPLICATION_JSON)         
               .content(objectMapper.writeValueAsString(order)))
               .andExpect(status().isCreated());        
            
    }
    
}






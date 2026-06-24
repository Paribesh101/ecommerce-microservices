package com.ecommerce.orderservice.service;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import com.ecommerce.orderservice.exception.OutOfStockException;
import com.ecommerce.orderservice.kafka.OrderProducer;
import com.ecommerce.orderservice.model.Order;
import com.ecommerce.orderservice.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private OrderProducer orderProducer;

    @InjectMocks
    private OrderService orderService;

    @Test
    void placeOrder_whenInStock_savesAndReturnsOrder(){
        Order order = new Order();
        order.setProductName("Laptop");

        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);
        
        //Act
        Order result = orderService.placeOrder(order);

        // Assert
        assertEquals("PENDING", result.getOrderStatus());
        verify(orderRepository).save(order);
        verify(orderProducer).sendOrderEvent(anyString());
    }
    
    @Test
    void placeOrder_whenOutOfStock_throwsExceptionAndDoesNotSave(){
        // Arrange
        Order order = new Order();
        order.setProductName("Mouse");

        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);

        // Act + Assert
        assertThrows(OutOfStockException.class, () -> orderService.placeOrder(order));

        // Verify nothing was saved or sent
        verify(orderRepository, never()).save(order);
        verify(orderProducer, never()).sendOrderEvent(anyString());

    }

}

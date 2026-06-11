package com.ecommerce.notificationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-topic", groupId = "notification-group")
    public void consumeOrderEvent(String message) {
        System.out.println("Notification received: " + message);
    }

}
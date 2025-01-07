package com.example.notificationservice.listener;

import com.example.notificationservice.model.Order;
import org.springframework.kafka.annotation.KafkaListener;

public class OrderListener {

    @KafkaListener(topics = "notification-topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consumer1(Order order) {
        System.out.println("This is first consumer... "+order);
    }


}

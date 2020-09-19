package com.java_school.apachekafkaservice.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @KafkaListener(topics = "usersChoices", groupId = "group_id")
    public void consume(String message) {
        System.out.println(message);
    }
}
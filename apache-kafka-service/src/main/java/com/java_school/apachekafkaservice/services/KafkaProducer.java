package com.java_school.apachekafkaservice.services;

import com.java_school.apachekafkaservice.dto.UserChoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final String TOPIC = "usersChoices";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(UserChoiceDTO userChoiceDTO) {
        System.out.println("Send message to kafka: " + userChoiceDTO.toString());
        kafkaTemplate.send(TOPIC, userChoiceDTO.toString());
    }
}

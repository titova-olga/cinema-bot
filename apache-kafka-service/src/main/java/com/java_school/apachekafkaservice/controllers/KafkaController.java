package com.java_school.apachekafkaservice.controllers;

import com.java_school.apachekafkaservice.dto.UserChoiceDTO;
import com.java_school.apachekafkaservice.services.Consumer;
import com.java_school.apachekafkaservice.services.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userChoice")
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    Consumer consumer;

    @PostMapping
    public void addUserChoiceToKafka(@RequestBody UserChoiceDTO userChoiceDTO) {
        kafkaProducer.sendMessage(userChoiceDTO);
    }

    @GetMapping("/all")
    public List<String> getAllMessages() {
        return consumer.getAllMessages();
    }

}

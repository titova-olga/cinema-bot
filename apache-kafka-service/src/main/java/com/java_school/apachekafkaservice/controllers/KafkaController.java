package com.java_school.apachekafkaservice.controllers;

import com.java_school.apachekafkaservice.services.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userChoice")
public class KafkaController {

    @Autowired
    KafkaProducer kafkaProducer;

    @PostMapping
    public void addUserChoiceToKafka(@RequestBody String userChoice) {
        kafkaProducer.sendMessage(userChoice);
    }
}

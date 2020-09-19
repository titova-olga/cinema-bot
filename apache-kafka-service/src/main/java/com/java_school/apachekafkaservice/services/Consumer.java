package com.java_school.apachekafkaservice.services;

import com.java_school.apachekafkaservice.dto.UserChoiceDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class Consumer {

    /*@KafkaListener(topics = "usersChoices", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumer:"+ message);
    }*/

    @KafkaListener(
            groupId = "group_id",
            topicPartitions = @TopicPartition(
                    topic = "usersChoices",
                    partitionOffsets = { @PartitionOffset(
                            partition = "0",
                            initialOffset = "0") }))
    public String listenToPartitionWithOffset(
            @Payload String userChoiceDTO) {
        System.out.println(userChoiceDTO.toString());
        return userChoiceDTO;
    }
}

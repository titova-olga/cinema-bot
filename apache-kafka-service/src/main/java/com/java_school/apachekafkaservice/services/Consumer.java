package com.java_school.apachekafkaservice.services;

import com.java_school.apachekafkaservice.dto.UserChoiceDTO;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class Consumer {

    /*@KafkaListener(topics = "usersChoices", groupId = "group_id")
    public void consume(String message) {
        System.out.println("Consumer:"+ message);
    }*/

    @Getter
    private List<String> allMessages = new ArrayList<>();

    @KafkaListener(
            groupId = "group_id",
            topicPartitions = @TopicPartition(
                    topic = "usersChoices",
                    partitionOffsets = { @PartitionOffset(
                            partition = "0",
                            initialOffset = "0") }))
    public void listenToPartitionWithOffset(
            @Payload String userChoiceDTO) {
        System.out.println(userChoiceDTO.toString());
        allMessages.add(userChoiceDTO);
    }
}

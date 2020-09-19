package com.java_school.informator.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaConsumer {
    private final ConsumerConnector consumerConnector;
    private final String topic = "usersChoices2";
    private final String group_id = "group_id2";
    private final String zookeeperConnect = "localhost:2181";

    public KafkaConsumer() {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeperConnect);
        props.put("group.id", group_id);
        props.put("zookeeper.session.timeout.ms", "2000");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("auto.commit.enable", "false");
        props.put("consumer.timeout.ms", "5000");

        this.consumerConnector = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }
}
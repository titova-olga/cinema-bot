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
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final ConsumerConnector consumer;
    private final String topic = "usersChoices";
    private final String group_id = "group_id";
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

        this.consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
    }

    public void getData() {
        List<String> msgs = new ArrayList<>();
        Map<String, Integer> topicMap = new HashMap<>();

        // Define single thread for topic
        topicMap.put(topic, 1);
        try {
            Map<String, List<KafkaStream<byte[], byte[]>>> listMap = consumer.createMessageStreams(topicMap);
            List<KafkaStream<byte[], byte[]>> kafkaStreams = listMap.get(topic);

            // Collect the messages.
            kafkaStreams.forEach(ks -> ks.forEach(mam -> msgs.add(new String(mam.message()))));

        } catch (ConsumerTimeoutException exception) {
            msgs.forEach(System.out::println);
        } finally {
            if (consumer != null) {
                consumer.shutdown();
            }
        }
    }
}
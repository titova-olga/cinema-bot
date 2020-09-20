package com.java_school.informator.kafka;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.java_school.informator.users_choices_cache.UserChoice;
import com.java_school.informator.users_choices_cache.UsersChoicesCache;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class KafkaConsumer {
    @Autowired
    private UsersChoicesCache usersChoicesCache;

    private final ConsumerConnector consumerConnector;

    @Value("${kafka.topic}")
    private String topic;

    public KafkaConsumer(@Value("${kafka.zookeeper_connect}") String zookeeperConnect,
                         @Value("${kafka.group_id}") String group_id) {
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

    public void fillCache() {
        Map<String, Integer> topicMap = new HashMap<>();
        topicMap.put(topic, 1); // Define single thread for topic
        try {
            Map<String, List<KafkaStream<byte[], byte[]>>> listMap =
                    consumerConnector.createMessageStreams(topicMap);
            List<KafkaStream<byte[], byte[]>> kafkaStreams = listMap.get(topic);

            kafkaStreams.forEach(ks -> ks.forEach(mam -> parseUserChoice(new String(mam.message()))));

        } catch (ConsumerTimeoutException exception) {
            System.out.println("Filling cache ends");
        } finally {
            if (consumerConnector != null) {
                consumerConnector.shutdown();
            }
        }
    }

    private void parseUserChoice(String message) {
        System.out.println(message);
        int startParamsInd = message.indexOf("{");
        int endParamsInd = message.indexOf("}");
        if(startParamsInd == -1|| endParamsInd == -1)
            return;

        String className = message.substring(0, startParamsInd);

        //Object obj = Class.forName(className).getDeclaredConstructor().newInstance();

        if (className.equals("ClearUserChoiceDTO")) {
            int chatId = Integer.parseInt(message
                    .substring(startParamsInd + 1, endParamsInd)
                    .split("=")[1]);
            usersChoicesCache.removeInfoAboutUserChoices(chatId);
        } else {
            String[] parameters = message.substring(startParamsInd + 1, endParamsInd)
                    .split(",");
            int chatId = Integer.parseInt(parameters[0].split("=")[1]);

            if (className.equals("FilmUserChoiceDTO")) {
                int choiceId = Integer.parseInt(parameters[1].split("=")[1]);
                usersChoicesCache.addFilmChoice(chatId, choiceId);
            }
            if (className.equals("CinemaUserChoiceDTO")) {
                int choiceId = Integer.parseInt(parameters[1].split("=")[1]);
                usersChoicesCache.addCinemaChoice(chatId, choiceId);
            }
            if (className.equals("DateUserChoiceDTO")) {
                LocalDate date = LocalDate.parse(parameters[1].split("=")[1]);
                usersChoicesCache.addDateChoice(chatId, date);
            }
        }
    }
}
package com.java_school.informator.users_choices_cache;


import com.java_school.informator.kafka.KafkaConsumer;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Component
public class UsersChoicesCache {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    private final Map<Long, UserChoice> usersChoicesMap = new HashMap<>();

    public void addFilmChoice(long chatId, int filmId) {
        getOrCreateUserChoice(chatId).addFilmChoice(filmId);
    }

    public void addCinemaChoice(long chatId, int cinemaId) {
        getOrCreateUserChoice(chatId).addCinemaChoice(cinemaId);
    }

    public void addDateChoice(long chatId, LocalDate date) {
        getOrCreateUserChoice(chatId).addDateChoice(date);
    }

    private UserChoice getOrCreateUserChoice(long chatId) {
        if (!usersChoicesMap.containsKey(chatId)) {
            usersChoicesMap.put(chatId, new UserChoice());
        }
        return usersChoicesMap.get(chatId);
    }

    public UserChoice getUserChoice(long chatId){
        return usersChoicesMap.get(chatId);
    }

    public void removeInfoAboutUserChoices(long chatId) {
        usersChoicesMap.remove(chatId);
    }

    //@EventListener(ContextRefreshedEvent.class)
    @PostConstruct
    public void fillCacheFromKafkaOnRestart() {
        kafkaConsumer.getData();

        /*Properties props = new Properties();
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "group_id");
        props.put("zookeeper.session.timeout.ms", "2000");
        props.put("zookeeper.sync.time.ms", "250");
        props.put("auto.commit.interval.ms", "1000");
        props.put("auto.offset.reset", "smallest");
        props.put("auto.commit.enable", "false");
        props.put("consumer.timeout.ms", "5000");

        ConsumerConnector consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));

        List<byte[]> msgs = new ArrayList<>();
        Map<String, Integer> topicMap = new HashMap<>();

            // Define single thread for topic
        topicMap.put("usersChoices", 1);
        try {
            Map<String, List<KafkaStream<byte[], byte[]>>> listMap = consumer.createMessageStreams(topicMap);
            List<KafkaStream<byte[], byte[]>> kafkaStreams = listMap.get("usersChoices");

            // Collect the messages.
            kafkaStreams.forEach(ks -> ks.forEach(mam -> msgs.add(mam.message())));

        } catch (ConsumerTimeoutException exception) {
            msgs.forEach(System.out::println);
        } finally {
            consumer.shutdown();
        }*/
    }
}

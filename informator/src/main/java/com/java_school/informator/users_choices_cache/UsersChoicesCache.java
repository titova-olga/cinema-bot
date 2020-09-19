package com.java_school.informator.users_choices_cache;


import com.java_school.informator.dto.CinemaUserChoiceDTO;
import com.java_school.informator.dto.ClearUserChoiceDTO;
import com.java_school.informator.dto.DateUserChoiceDTO;
import com.java_school.informator.dto.FilmUserChoiceDTO;
import com.java_school.informator.kafka.KafkaConsumer;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import lombok.SneakyThrows;
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
        getData();

    }

    private void getData() {
        Map<String, Integer> topicMap = new HashMap<>();
        // Define single thread for topic
        topicMap.put(kafkaConsumer.getTopic(), 1);
        try {
            Map<String, List<KafkaStream<byte[], byte[]>>> listMap =
                    kafkaConsumer.getConsumerConnector().createMessageStreams(topicMap);
            List<KafkaStream<byte[], byte[]>> kafkaStreams = listMap.get(kafkaConsumer.getTopic());

            // Collect the messages.
            kafkaStreams.forEach(ks -> ks.forEach(mam -> parseUserChoice(new String(mam.message()))));

        } catch (ConsumerTimeoutException exception) {
            System.out.println("Filling cache ends");
        } finally {
            if (kafkaConsumer.getConsumerConnector() != null) {
                //kafkaConsumer.getConsumerConnector().shutdown();
            }
        }
    }

    @SneakyThrows
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
            removeInfoAboutUserChoices(chatId);
        } else {
            String[] parameters = message.substring(startParamsInd + 1, endParamsInd)
                    .split(",");
            int chatId = Integer.parseInt(parameters[0].split("=")[1]);

            if (className.equals("FilmUserChoiceDTO")) {
                int choiceId = Integer.parseInt(parameters[1].split("=")[1]);
                addFilmChoice(chatId, choiceId);
            }
            if (className.equals("CinemaUserChoiceDTO")) {
                int choiceId = Integer.parseInt(parameters[1].split("=")[1]);
                addCinemaChoice(chatId, choiceId);
            }
            if (className.equals("DateUserChoiceDTO")) {
                LocalDate date = LocalDate.parse(parameters[1].split("=")[1]);
                addDateChoice(chatId, date);
            }
        }
    }
}

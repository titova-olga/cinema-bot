package com.java_school.informator.users_choices_cache;


import com.java_school.informator.kafka.KafkaConsumer;
import kafka.consumer.ConsumerTimeoutException;
import kafka.consumer.KafkaStream;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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

    @PostConstruct
    public void fillCacheFromKafkaOnRestart() {
        kafkaConsumer.fillCache();
        System.out.println();
    }
}

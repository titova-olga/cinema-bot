package com.java_school.informator.users_choices_cache;

import com.java_school.informator.dto.UserChoiceDTO;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class UsersChoicesCache {
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
    public void fillCacheFromKafkaOnRestart() {

    }
}

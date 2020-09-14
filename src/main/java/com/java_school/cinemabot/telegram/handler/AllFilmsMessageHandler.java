package com.java_school.cinemabot.telegram.handler;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AllFilmsMessageHandler implements MessageHandler {
    @Autowired
    private DatabaseFilmService databaseFilmService;

    @Override
    public String generateAnswer(Update update) {
        //return  Stream.of("Film1: www.yandex.ru", "Film2: www.google.com").collect(Collectors.joining("\n"));
        return databaseFilmService.getAllFilms().stream().map(Film::getName).collect(Collectors.joining("\n"));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_FILMS;
    }
}

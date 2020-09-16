package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AllFilmsMessageHandler implements MessageHandler {
    @Autowired
    private DatabaseFilmService databaseFilmService;

    @Override
    public SendMessage generateAnswer(Update update) {
        List<Film> films = databaseFilmService.getAllFilms();
        String filmsAnswer = IntStream.range(0, films.size())
                .mapToObj(i -> {
                    return (i + 1) + ". " + films.get(i).getName() + " /film_" + films.get(i).getId();
                })
                .collect(Collectors.joining("\n"));
        SendMessage answer = new SendMessage();
        answer.setText(filmsAnswer);
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_FILMS;
    }
}
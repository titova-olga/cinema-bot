package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AllCinemasMessageHandler implements MessageHandler {

    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @Override
    public SendMessage generateAnswer(Update update) {
        List<Cinema> cinemas = databaseCinemaService.getAllCinemas();
        String cinemasAnswer = IntStream.range(0, cinemas.size())
                .mapToObj(i -> {
                    return (i + 1) + ". " + cinemas.get(i).getName() + " /cinema_" + cinemas.get(i).getId();
                })
                .collect(Collectors.joining("\n"));
        SendMessage answer = new SendMessage();
        answer.setText(cinemasAnswer);
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_CINEMAS;
    }
}

package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import com.java_school.cinemabot.telegram.cache.ResponseType;
import com.java_school.cinemabot.telegram.cache.UsersResponsesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SessionsHandler implements MessageHandler {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_SESSIONS;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        Map<ResponseType, Integer> usersResponses = UsersResponsesCache.getUsersResponses(chatId);

        if(usersResponses.size() == 1 && usersResponses.containsKey(ResponseType.FILM)) {
            List<Session> sessions = databaseSessionService.getSessionsByFilmId(usersResponses.get(ResponseType.FILM));
            String sessionsAnswer = IntStream.range(0, sessions.size())
                    .mapToObj(i -> {
                        return (i + 1) + ". "
                                + sessions.get(i).getCinema().getName() + ": "
                                + sessions.get(i).getTime() + " - "
                                + sessions.get(i).getPrice()
                                + " /session_" + sessions.get(i).getId();
                    })
                    .collect(Collectors.joining("\n"));
            SendMessage answer = new SendMessage();
            answer.setText(sessionsAnswer);
            return answer;
        }
        else if(usersResponses.size() == 2
                && usersResponses.containsKey(ResponseType.FILM)
                && usersResponses.containsKey(ResponseType.CINEMA)) {
            List<Session> sessions = databaseSessionService
                    .getSessionsByFilmAndCinema(usersResponses.get(ResponseType.FILM),
                                        usersResponses.get(ResponseType.CINEMA));
            String sessionsAnswer = IntStream.range(0, sessions.size())
                    .mapToObj(i -> {
                        return (i + 1) + ". "
                                + sessions.get(i).getTime() + " - "
                                + sessions.get(i).getPrice()
                                + " /session_" + sessions.get(i).getId();
                    })
                    .collect(Collectors.joining("\n"));
            SendMessage answer = new SendMessage();
            answer.setText(sessionsAnswer);
            return answer;
        }

        UsersResponsesCache.removeInfoAboutUserResponses(chatId); // todo

        return null;
    }
}

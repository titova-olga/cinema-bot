package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import com.java_school.bot.model.Session;
import com.java_school.bot.telegram.cache.ResponseType;
import com.java_school.bot.telegram.cache.UsersResponsesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SessionsHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_SESSIONS;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        Map<ResponseType, Integer> usersResponses = UsersResponsesCache.getUsersResponses(chatId);

        if (usersResponses.size() == 1 && usersResponses.containsKey(ResponseType.FILM)) {
            String sessionsAnswer = "";
            int filmId = usersResponses.get(ResponseType.FILM);
            Session[] sessionsResponse = restTemplate.getForObject(RestUrls.FILMS + "/" + filmId + "/sessions", Session[].class);
            if(sessionsResponse != null){
                List<Session> sessions = Arrays.asList(sessionsResponse);
                sessionsAnswer = IntStream.range(0, sessions.size())
                        .mapToObj(i -> {
                            return (i + 1) + ". "
                                    + sessions.get(i).getCinema().getName() + ": "
                                    + sessions.get(i).getTime() + " - "
                                    + sessions.get(i).getPrice()
                                    + " /session_" + sessions.get(i).getId();
                        })
                        .collect(Collectors.joining("\n"));
            }
            SendMessage answer = new SendMessage();
            answer.setText(sessionsAnswer);
            return answer;
        } else if (usersResponses.size() == 2
                && usersResponses.containsKey(ResponseType.FILM)
                && usersResponses.containsKey(ResponseType.CINEMA)) {
            /*List<Session> sessions = databaseSessionService
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
            return answer;*/
            SendMessage answer = new SendMessage();
            answer.setText("");
            return answer;
        }

        UsersResponsesCache.removeInfoAboutUserResponses(chatId); // todo

        return null;
    }
}

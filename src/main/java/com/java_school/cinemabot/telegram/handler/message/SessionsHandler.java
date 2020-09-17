package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import com.java_school.cinemabot.telegram.cache.ResponseType;
import com.java_school.cinemabot.telegram.cache.UsersResponsesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<ResponseType, List<Integer>> usersResponses = UsersResponsesCache.getUsersResponses(chatId);

        if (usersResponses == null) {
            SendMessage answer = new SendMessage();
            answer.setText("Выбери хотя бы один подходящий тебе фильм, кинотетатр или дату" +
                    " и тогда ты сможешь посмотреть сеансы!");
            return answer;
        }

        List<Session> sessions = databaseSessionService.getSessionsByFilmIdsCinemaIdsDatesAndIfAbsentGetAll(usersResponses.get(ResponseType.FILM),
                usersResponses.get(ResponseType.CINEMA),
                new ArrayList<>());

        StringBuilder sessionsAnswer = new StringBuilder();
        sessionsAnswer.append("НАЙДЕННЫЕ СЕАНСЫ:\n");

        String cinemaName = null;
        String filmName = null;
        for(int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);

            String curCinemaName = session.getCinema().getName();
            if(!curCinemaName.equals(cinemaName)) {
                filmName = null;
                cinemaName = curCinemaName;
                sessionsAnswer.append("\n" + Stickers.CINEMA.getCode() + cinemaName + "\n");
            }

            String curFilmName = session.getFilm().getName();
            if(!curFilmName.equals(filmName)) {
                filmName = curFilmName;
                sessionsAnswer.append("\n" + Stickers.FILM.getCode() + filmName + "\n\n");
            }

            sessionsAnswer.append("- " + sessions.get(i).getTime() + " "
                                + Stickers.MONEY.getCode() +  sessions.get(i).getPrice()
                                + " /session_" + sessions.get(i).getId() + "\n");
        }

        String res = sessionsAnswer.toString();
        UsersResponsesCache.removeInfoAboutUserResponses(chatId);

        SendMessage answer = new SendMessage();
        //answer.enableMarkdown(true);
        answer.setText(res);
        return answer;

    }
}

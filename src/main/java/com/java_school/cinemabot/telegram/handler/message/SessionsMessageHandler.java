package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import com.java_school.cinemabot.telegram.cache.UserChoice;
import com.java_school.cinemabot.telegram.cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SessionsMessageHandler implements MessageHandler {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_SESSIONS;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        Long chatId = update.getMessage().getChatId();
        UserChoice userChoice = UsersChoicesCache.getUserChoice(chatId);

        if (userChoice == null) {
            return userChoiceAbsent();
        }

        List<Session> sessions = databaseSessionService
                .getSessionsByFilmIdsCinemaIdsDatesAndIfAbsentGetAll(userChoice.getFilmIds(),
                                                                    userChoice.getCinemaIds(),
                                                                    userChoice.getDates());

        if (sessions.size() == 0) {
            return sessionsByChoiceAbsent();
        }
        else {
            return getSessionsByChoice(sessions, chatId);
        }
    }

    private SendMessage userChoiceAbsent() {
        SendMessage answer = new SendMessage();
        answer.setText("Выбери хотя бы один подходящий тебе фильм, кинотетатр или дату" +
                    " и тогда ты сможешь посмотреть сеансы!");

        return answer;
    }

    private SendMessage sessionsByChoiceAbsent() {
        SendMessage answer = new SendMessage();
        answer.setText("К сожалению, по твоему запросу нет сеансов, попробуй выбрать что-нибудь другое...");

        return answer;
    }

    private SendMessage getSessionsByChoice(List<Session> sessions, Long chatId) {
        SendMessage answer = new SendMessage();

        StringBuilder sessionsAnswer = new StringBuilder();
        sessionsAnswer.append("<b>НАЙДЕННЫЕ СЕАНСЫ:</b>\n");

        String cinemaName = null;
        String filmName = null;
        for(int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);

            String curCinemaName = session.getCinema().getName();
            if(!curCinemaName.equals(cinemaName)) {
                filmName = null;
                cinemaName = curCinemaName;
                sessionsAnswer.append("\n" + Stickers.CINEMA + "<b><i>" + cinemaName + "</i></b>" + "\n");
            }

            String curFilmName = session.getFilm().getName();
            if(!curFilmName.equals(filmName)) {
                filmName = curFilmName;
                sessionsAnswer.append("\n" + Stickers.FILM + "<b><i>" + filmName + "</i></b>" + "\n\n");
            }

            sessionsAnswer.append("- " + sessions.get(i).getTime() + " "
                    + Stickers.MONEY.getCode() +  sessions.get(i).getPrice()
                    + " /session_" + sessions.get(i).getId() + "\n");
        }

        String res = sessionsAnswer.toString();
        UsersChoicesCache.removeInfoAboutUserChoices(chatId);

        answer.setText(res);
        answer.enableHtml(true);

        return answer;
    }
}

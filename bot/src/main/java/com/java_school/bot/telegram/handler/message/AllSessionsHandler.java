package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.dto.ClearUserChoiceDTO;
import com.java_school.bot.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;

@Component
public class AllSessionsHandler implements MessageHandler {

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

        Session[] sessionsResponse = getSessionsByUserChoice(chatId);
        clearUserChoice(chatId);

        if(sessionsResponse != null){
            List<Session> sessions = Arrays.asList(sessionsResponse);
            if (sessions.size() == 0) {
                return answerSessionsByChoiceAbsent();
            }
            return answerSessionsByChoice(sessions, chatId);
        }else{
            return answerUserChoiceAbsent();
        }
    }

    private Session[] getSessionsByUserChoice(long chatId) {
        return restTemplate.getForObject(RestUrls.SESSIONS + "?chatId=" + chatId, Session[].class);
    }

    private void clearUserChoice(long chatId) {
        restTemplate.postForLocation(RestUrls.USER_CHOICE, new ClearUserChoiceDTO(chatId));
    }

    private SendMessage answerUserChoiceAbsent() {
        SendMessage answer = new SendMessage();
        answer.setText("Выбери хотя бы один подходящий тебе фильм, кинотетатр или дату" +
                " и тогда ты сможешь посмотреть сеансы!");

        return answer;
    }

    private SendMessage answerSessionsByChoiceAbsent() {
        SendMessage answer = new SendMessage();
        answer.setText("К сожалению, по твоему запросу нет сеансов, попробуй выбрать что-нибудь другое...");

        return answer;
    }

    private SendMessage answerSessionsByChoice(List<Session> sessions, Long chatId) {
        SendMessage answer = new SendMessage();

        StringBuilder sessionsAnswer = new StringBuilder();
        sessionsAnswer.append("<b>НАЙДЕННЫЕ СЕАНСЫ:</b>\n");

        LocalDate date = null;
        String cinemaName = null;
        String filmName = null;
        for (Session session : sessions) {
            LocalDate curDate = session.getDate();
            if (!curDate.equals(date)) {
                filmName = null;
                cinemaName = null;
                date = curDate;
                sessionsAnswer.append("\n" + "<b>"
                        + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
                        + "</b>" + "\n");
            }

            String curCinemaName = session.getCinema().getName();
            if (!curCinemaName.equals(cinemaName)) {
                filmName = null;
                cinemaName = curCinemaName;
                sessionsAnswer.append("\n" + Stickers.CINEMA + "<b><i>" + cinemaName + "</i></b>" + "\n");
            }

            String curFilmName = session.getFilm().getName();
            if (!curFilmName.equals(filmName)) {
                filmName = curFilmName;
                sessionsAnswer.append("\n" + Stickers.FILM + "<b><i>" + filmName + "</i></b>" + "\n\n");
            }

            sessionsAnswer.append("- " + session.getTime() + " "
                    + Stickers.MONEY.getCode() + session.getPrice()
                    + " /session_" + session.getId() + "\n");
        }

        String res = sessionsAnswer.toString();

        answer.setText(res);
        answer.enableHtml(true);

        return answer;
    }
}

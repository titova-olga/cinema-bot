package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Film;
import com.java_school.bot.model.Session;
import com.java_school.bot.telegram.cache.UserChoice;
import com.java_school.bot.telegram.cache.UsersChoicesCache;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AllSessionsHandler implements MessageHandler {

    @Autowired
    private UsersChoicesCache usersChoicesCache;

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
        UserChoice userChoice = usersChoicesCache.getUserChoice(chatId);

        if (userChoice == null) {
            return userChoiceAbsent();
        }

        StringBuilder params = new StringBuilder("?");
        String filmsId = convertListToStringWithDelimiter(userChoice.getFilmIds(), ",");
        params.append("films=").append(filmsId);
        String cinemasId = convertListToStringWithDelimiter(userChoice.getCinemaIds(), ",");
        params.append("&cinemas=").append(cinemasId);
        String datesId = convertListToStringWithDelimiter(userChoice.getDates(), ",");
        params.append("&dates=").append(datesId);

        Session[] sessionsResponse = restTemplate.getForObject(RestUrls.SESSIONS + params.toString(), Session[].class);
        if(sessionsResponse != null){
            List<Session> sessions = Arrays.asList(sessionsResponse);
            return getSessionsByChoice(sessions, chatId);
        }else{
            return sessionsByChoiceAbsent();
        }
    }

    private String convertListToStringWithDelimiter(List<?> list, String delimiter){
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
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

        LocalDate date = null;
        String cinemaName = null;
        String filmName = null;
        for(int i = 0; i < sessions.size(); i++) {
            Session session = sessions.get(i);

            LocalDate curDate = session.getDate();
            if(!curDate.equals(date)) {
                filmName = null;
                cinemaName = null;
                date = curDate;
                sessionsAnswer.append("\n" + "<b>"
                        + date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
                        + "</b>" + "\n");
            }

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
        usersChoicesCache.removeInfoAboutUserChoices(chatId);

        answer.setText(res);
        answer.enableHtml(true);

        return answer;
    }
}

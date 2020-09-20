package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Session;
import com.java_school.bot.telegram.cache.SessionsPaginationCache;
import com.java_school.bot.telegram.handlers.pagination.PaginationCreator;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Component
public class AllSessionsHandler implements MessageHandler {

    @Autowired
    private SessionsPaginationCache sessionsPaginationCache;

    @Autowired
    private PaginationCreator paginationCreator;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public boolean isGeneratingNewMessage(Update update) {
        String messageText = update.hasMessage()
                ? update.getMessage().getText()
                : update.getCallbackQuery().getData();
        String[] s = messageText.split(" ");
        return s.length == 1;
    }

    @Override
    public EditMessageText editMessage(Update update) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        String messageText = update.getCallbackQuery().getData();

        Session[] sessionsResponse = getSessionsByUserChoice(chatId);
        List<Session> sessions = Arrays.asList(sessionsResponse);

        String[] s = messageText.split(" ");
        Message message = generateSessionsByChoice(sessions, chatId, parseInt(s[1]));
        return new EditMessageText()
                .setText(message.getMessage())
                .setReplyMarkup(message.getKeyboard())
                .enableHtml(true);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_SESSIONS;
    }

    @Override
    public SendMessage generateMessage(Update update) {
        long chatId = update.getMessage().getChatId();

        Session[] sessionsResponse = getSessionsByUserChoice(chatId);

        if(sessionsResponse != null){
            List<Session> sessions = Arrays.asList(sessionsResponse);
            if (sessions.size() == 0) {
                return answerSessionsByChoiceAbsent();
            }

            Message message = generateSessionsByChoice(sessions, chatId,0);
            return new SendMessage()
                    .setText(message.getMessage())
                    .setReplyMarkup(message.getKeyboard())
                    .enableHtml(true);
        }else{
            return answerUserChoiceAbsent();
        }
    }

    private Session[] getSessionsByUserChoice(long chatId) {
        return restTemplate.getForObject(RestUrls.SESSIONS + "?chatId=" + chatId, Session[].class);
    }

    private String convertListToStringWithDelimiter(List<?> list, String delimiter){
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(delimiter));
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

    private Message generateSessionsByChoice(List<Session> sessions,
                                            long chatId,
                                            int curPage) {
        int curSessionInd = sessionsPaginationCache.getSessionInd(chatId, curPage);
        int nextSessionInd = getNextSessionIndex(sessions, chatId, curPage);

        Message message = new Message();
        message.setMessage(generateMessage(sessions, curSessionInd, nextSessionInd));

        boolean nextButton = nextSessionInd < sessions.size();
        if (curSessionInd > 0 || nextButton) {
            message.setKeyboard(paginationCreator.createPagination(getMessageType(),
                                                        curPage,
                                                        nextButton));
        }
        return message;
    }

    private int getNextSessionIndex(List<Session> sessions, long chatId, int curPage) {
        int curSessionInd = sessionsPaginationCache.getSessionInd(chatId, curPage);
        int nextSessionInd;
        if (sessionsPaginationCache.hasSessionInd(chatId, curPage + 1)) {
            nextSessionInd = sessionsPaginationCache.getSessionInd(chatId, curPage + 1);
        } else {
            nextSessionInd = curSessionInd;
            int lines = 0;
            var nextGenerateSessions = new NextGenerateSessions(sessions, curSessionInd);
            while (nextSessionInd < sessions.size()) {
                var newNext = nextGenerateSessions.getNextSessions();
                if (nextSessionInd + newNext + lines + 3 - curSessionInd > 25) {
                    if (lines == 0)
                        nextSessionInd += newNext;
                    break;
                }
                nextSessionInd += newNext;
                lines += 3;
            }
            sessionsPaginationCache.addNextSessionInd(chatId, nextSessionInd);
        }
        return nextSessionInd;
    }

    private String generateMessage(List<Session> sessions, int startInd, int endInd) {
        StringBuilder sessionsAnswer = new StringBuilder();
        sessionsAnswer.append("<b>НАЙДЕННЫЕ СЕАНСЫ:</b>\n");

        String curCinema = null, curFilm = null;
        LocalDate curDate = null;
        for (int i = startInd; i < endInd; i++) {
            Session startSession = sessions.get(i);
            String cinemaName = startSession.getCinema().getName();
            String filmName = startSession.getFilm().getName();
            LocalDate date = startSession.getDate();

            if (!date.equals(curDate)) {
                curDate = date;
                curCinema = null;
                curFilm = null;
                sessionsAnswer
                        .append("\n" + "<b>")
                        .append(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)))
                        .append("</b>")
                        .append("\n");
            }


            if (!cinemaName.equals(curCinema)) {
                curCinema = cinemaName;
                curFilm = null;
                sessionsAnswer.append("\n")
                        .append(Stickers.CINEMA)
                        .append("<b><i>")
                        .append(cinemaName)
                        .append("</i></b>")
                        .append("\n");
            }
            if (!filmName.equals(curFilm)) {
                curFilm = filmName;
                sessionsAnswer.append("\n")
                        .append(Stickers.FILM)
                        .append("<b><i>")
                        .append(filmName)
                        .append("</i></b>")
                        .append("\n\n");
            }

            sessionsAnswer
                    .append("- ")
                    .append(sessions.get(i).getTime())
                    .append(" ")
                    .append(Stickers.MONEY.getCode())
                    .append(sessions.get(i).getPrice())
                    .append(" /session_")
                    .append(sessions.get(i).getId())
                    .append("\n");
        }
        return sessionsAnswer.toString();
    }

    @Data
    private class Message {
        private String message;
        private InlineKeyboardMarkup keyboard;
    }


    private class NextGenerateSessions {
        private int sessionId;
        private final List<Session> sessions;

        public NextGenerateSessions(List<Session> sessions, int startSessionId) {
            this.sessionId = startSessionId;
            this.sessions = sessions;
        }

        public int getNextSessions() {
            int i = sessionId;
            Session startSession = sessions.get(i);
            String cinemaName = startSession.getCinema().getName();
            String filmName = startSession.getFilm().getName();
            LocalDate date = startSession.getDate();

            for (; i < sessions.size(); i++) {
                Session session = sessions.get(i);
                String curCinemaName = session.getCinema().getName();
                String curFilmName = session.getFilm().getName();
                LocalDate curDate = session.getDate();

                if (!curCinemaName.equals(cinemaName)
                        || !curFilmName.equals(filmName)
                        || !curDate.equals(date)) {
                    int diff = i - sessionId;
                    sessionId = i;
                    return diff;
                }
            }
            return sessions.size() - sessionId;
        }
    }
}

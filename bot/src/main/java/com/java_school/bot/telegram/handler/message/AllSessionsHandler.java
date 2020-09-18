package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Session;
import com.java_school.bot.telegram.cache.SessionsPaginationCache;
import com.java_school.bot.telegram.cache.UserChoice;
import com.java_school.bot.telegram.cache.UsersChoicesCache;
import com.java_school.bot.telegram.handler.pagination.PaginationCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    private UsersChoicesCache usersChoicesCache;

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
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();
        String messageText = update.hasMessage()
                ? update.getMessage().getText()
                : update.getCallbackQuery().getData();
        UserChoice userChoice = usersChoicesCache.getUserChoice(chatId);
        var sessionsResponse = getSessions(userChoice);
        List<Session> sessions = Arrays.asList(sessionsResponse);
        String[] s = messageText.split(" ");
        return editSessionsByChoice(sessions, parseInt(s[1]));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_SESSIONS;
    }
    @Override
    public SendMessage generateMessage(Update update) {
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();
        String messageText = update.hasMessage()
                ? update.getMessage().getText()
                : update.getCallbackQuery().getData();
        UserChoice userChoice = usersChoicesCache.getUserChoice(chatId);
        if (userChoice == null)
            return userChoiceAbsent();

        var sessionsResponse = getSessions(userChoice);
        if (sessionsResponse == null)
            return sessionsByChoiceAbsent();

        List<Session> sessions = Arrays.asList(sessionsResponse);
        String[] s = messageText.split(" ");
        if (s.length == 1) {
            sessionsPaginationCache.addNextSessionInd(0);
            return generateSessionsByChoice(sessions, 0);
        }
        return generateSessionsByChoice(sessions, parseInt(s[1]));
    }

    private Session[] getSessions(UserChoice userChoice) {
        StringBuilder params = new StringBuilder("?");
        String filmsId = convertListToStringWithDelimiter(userChoice.getFilmIds(), ",");
        params.append("films=").append(filmsId);
        String cinemasId = convertListToStringWithDelimiter(userChoice.getCinemaIds(), ",");
        params.append("&cinemas=").append(cinemasId);
        String datesId = convertListToStringWithDelimiter(userChoice.getDates(), ",");
        params.append("&dates=").append(datesId);

        return restTemplate.getForObject(RestUrls.SESSIONS + params.toString(), Session[].class);
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

    private SendMessage generateSessionsByChoice(List<Session> sessions,
                                            int curPage) {
        int curSessionInd = sessionsPaginationCache.getSessionInd(curPage);
        int nextSessionInd = getNextSessionIndex(sessions, curPage);

        SendMessage answer = new SendMessage();
        answer.setText(generateMessage(sessions, curSessionInd, nextSessionInd));
        answer.enableHtml(true);

        boolean nextButton = nextSessionInd < sessions.size();
        if (curSessionInd > 0 || nextButton) {
            answer.setReplyMarkup(paginationCreator.createPagination(getMessageType(),
                                                        curPage,
                                                        nextButton));
        }

        return answer;
    }

    private EditMessageText editSessionsByChoice(List<Session> sessions,
                                                 int curPage) {
        int curSessionInd = sessionsPaginationCache.getSessionInd(curPage);
        int nextSessionInd = getNextSessionIndex(sessions, curPage);

        EditMessageText answer = new EditMessageText();
        answer.setText(generateMessage(sessions, curSessionInd, nextSessionInd));
        answer.enableHtml(true);

        boolean nextButton = nextSessionInd < sessions.size();
        if (curSessionInd > 0 || nextButton) {
            answer.setReplyMarkup(paginationCreator.createPagination(getMessageType(),
                    curPage,
                    nextButton));
        }

        return answer;
    }

    private int getNextSessionIndex(List<Session> sessions, int curPage) {
        int curSessionInd = sessionsPaginationCache.getSessionInd(curPage);
        int nextSessionInd;
        if (sessionsPaginationCache.hasSessionInd(curPage + 1)) {
            nextSessionInd = sessionsPaginationCache.getSessionInd(curPage + 1);
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
            sessionsPaginationCache.addNextSessionInd(nextSessionInd);
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

package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import com.java_school.bot.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class AllFilmsMessageHandler implements MessageHandler {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public SendMessage generateMessage(Update update) {
        Film[] filmsResponse = getAllFilms();

        if(filmsResponse != null) {
            return generateExpectedAnswer(filmsResponse);
        }
        return generateErrorAnswer();
    }

    private Film[] getAllFilms() {
        try {
            return restTemplate.getForObject(RestUrls.FILMS, Film[].class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private SendMessage generateExpectedAnswer(Film[] filmsResponse) {
        String filmsAnswer;
        List<Film> films = Arrays.asList(filmsResponse);
        filmsAnswer = IntStream.range(0, films.size())
                .mapToObj(i -> {
                    return (i + 1) + ". " + films.get(i).getName() + " /film_" + films.get(i).getId();
                })
                .collect(Collectors.joining("\n"));

        SendMessage answer = new SendMessage();
        answer.setText(filmsAnswer);
        return answer;
    }

    private SendMessage generateErrorAnswer() {
        SendMessage answer = new SendMessage();
        answer.setText("Извини, произошла внутренняя ошибка, " +
                "мы постараемся решить ее как можно скорее... Попробуй отправить свой запрос попозже");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_FILMS;
    }
}

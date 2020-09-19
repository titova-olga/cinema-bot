package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
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
public class AllCinemasMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public SendMessage generateAnswer(Update update) {
        Cinema[] cinemasResponse = getAllCinemas();

        if(cinemasResponse != null) {
            return generateExpectedAnswer(cinemasResponse);
        }
        return generateErrorAnswer();
    }

    private Cinema[] getAllCinemas() {
        try {
            return restTemplate.getForObject(RestUrls.CINEMAS, Cinema[].class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private SendMessage generateExpectedAnswer(Cinema[] cinemasResponse) {
        String cinemasAnswer;
        List<Cinema> cinemas = Arrays.asList(cinemasResponse);
        cinemasAnswer = IntStream.range(0, cinemas.size())
                .mapToObj(i -> {
                    return (i + 1) + ". " + cinemas.get(i).getName() + " /cinema_" + cinemas.get(i).getId();
                })
                .collect(Collectors.joining("\n"));

        SendMessage answer = new SendMessage();
        answer.setText(cinemasAnswer);
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
        return MessageType.ALL_CINEMAS;
    }
}

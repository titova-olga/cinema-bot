package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.dto.CinemaUserChoiceDTO;
import com.java_school.bot.dto.FilmUserChoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class CinemaChoiceMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public MessageType getMessageType() {
        return MessageType.CINEMA_CHOICE;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        String response = update.getCallbackQuery().getData();//.split("/")[2];
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int choiceId = Integer.parseInt(response.split("/")[2].split("_")[1]);

        restTemplate.postForLocation(RestUrls.USER_CHOICE + "/cinema",
                new CinemaUserChoiceDTO(chatId, choiceId));

        SendMessage answer = new SendMessage();
        answer.setText("Продолжай дальше или посмотри сеансы, основанные на твоем выборе! ");
        return answer;
    }
}

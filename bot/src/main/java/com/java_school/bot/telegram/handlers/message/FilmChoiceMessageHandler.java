package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.dto.CinemaUserChoiceDTO;
import com.java_school.bot.dto.FilmUserChoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FilmChoiceMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public MessageType getMessageType() {
        return MessageType.FILM_CHOICE;
    }

    @Override
    public SendMessage generateMessage(Update update) {
        String response = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int choiceId = Integer.parseInt(response.split("/")[2].split("_")[1]);

        sendUserChoiceFilm(chatId, choiceId);

        SendMessage answer = new SendMessage();
        answer.setText("Продолжай дальше или посмотри сеансы, основанные на твоем выборе! ");
        return answer;
    }

    private void sendUserChoiceFilm(long chatId, int cinemaId) {
        try {
            restTemplate.postForLocation(RestUrls.USER_CHOICE_FILM,
                    new FilmUserChoiceDTO(chatId, cinemaId));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
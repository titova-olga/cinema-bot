package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.dto.ClearUserChoiceDTO;
import com.java_school.bot.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SessionMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public SendMessage generateMessage(Update update) {
        String sessionAnswer = "";
        int sessionId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Session session = restTemplate.getForObject(RestUrls.SESSIONS + "/" + sessionId, Session.class);
        if(session != null){
            sessionAnswer = "Ура! Держи ссылку на покупку билетов!\n" + session.getBuyReference();
        }
        Long chatId = update.getMessage().getChatId();
        clearUserChoice(chatId);
        SendMessage answer = new SendMessage();
        answer.setText(sessionAnswer);
        return answer;
    }

    private void clearUserChoice(long chatId) {
        restTemplate.postForLocation(RestUrls.USER_CHOICE, new ClearUserChoiceDTO(chatId));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.SESSION;
    }
}

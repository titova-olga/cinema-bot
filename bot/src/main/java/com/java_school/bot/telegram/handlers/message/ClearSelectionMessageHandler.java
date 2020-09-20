package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.dto.ClearUserChoiceDTO;
import com.java_school.bot.telegram.cache.SessionsPaginationCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ClearSelectionMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private SessionsPaginationCache sessionsPaginationCache;

    @Override
    public SendMessage generateMessage(Update update) {
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        clearUserChoice(chatId);
        sessionsPaginationCache.clearSelection(chatId);

        SendMessage answer = new SendMessage();
        answer.setText("Ваш выбор очищен. Продолжайте выбирать фильмы заново!");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CLEAR_SELECTION;
    }

    private void clearUserChoice(long chatId) {
        restTemplate.postForLocation(RestUrls.USER_CHOICE, new ClearUserChoiceDTO(chatId));
    }
}


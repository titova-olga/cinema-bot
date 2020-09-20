package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.telegram.cache.SessionsPaginationCache;
import com.java_school.bot.telegram.cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ClearSelectionMessageHandler implements MessageHandler {

    @Autowired
    private UsersChoicesCache usersChoicesCache;

    @Autowired
    private SessionsPaginationCache sessionsPaginationCache;

    @Override
    public SendMessage generateMessage(Update update) {
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        usersChoicesCache.removeInfoAboutUserChoices(chatId);
        sessionsPaginationCache.clearSelection(chatId);

        SendMessage answer = new SendMessage();
        answer.setText("Ваш выбор очищен. Продолжайте выбирать фильмы заново!");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CLEAR_SELECTION;
    }
}


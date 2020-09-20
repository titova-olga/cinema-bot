package com.java_school.bot.telegram;

import com.java_school.bot.telegram.handlers.message.MessageHandler;
import com.java_school.bot.telegram.handlers.message.MessageType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class MessageDistributor{
    private final Map<MessageType, MessageHandler> messageHandlerMap = new HashMap<>();

    public MessageDistributor(List<MessageHandler> messageHandlers) {
        for (MessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public boolean isGeneratingNewMessage(Update update) {
        MessageHandler messageHandler = getHandler(update);
        return messageHandler.isGeneratingNewMessage(update);
    }

    public EditMessageText editMessage(Update update) {
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        MessageHandler messageHandler = getHandler(update);
        var answer = messageHandler.editMessage(update);
        answer.setChatId(chatId);
        return answer;
    }

    public SendMessage generateMessage(Update update) {
        long chatId = update.hasMessage()
                ? update.getMessage().getChatId()
                : update.getCallbackQuery().getMessage().getChatId();

        MessageHandler messageHandler = getHandler(update);
        var answer = messageHandler.generateMessage(update);
        answer.setChatId(chatId);
        return answer;
    }

    private MessageHandler getHandler(Update update) {
        String messageText = update.hasMessage()
                ? update.getMessage().getText()
                : update.getCallbackQuery().getData();
        MessageType messageType = MessageType.getMessageType(messageText);
        return messageHandlerMap.get(messageType);
    }
}

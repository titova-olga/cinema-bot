package com.java_school.cinemabot.telegram;

import com.java_school.cinemabot.telegram.handler.DefaultMessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageDistributor {
    private Map<MessageType, MessageHandler> messageHandlerMap = new HashMap<>();

    public MessageDistributor(List<MessageHandler> messageHandlers) {
        for (MessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public String generateAnswer(Update update){
        String messageText = update.getMessage().getText();
        MessageType messageType = MessageType.findMessageHandler(messageText);
        MessageHandler messageHandler = messageHandlerMap.get(messageType);
        return messageHandler.generateAnswer(update);
    }
}

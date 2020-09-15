package com.java_school.cinemabot.telegram;

import com.java_school.cinemabot.telegram.handler.DefaultMessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageDistributor{
    private Map<MessageType, MessageHandler> messageHandlerMap = new HashMap<>();

    public MessageDistributor(List<MessageHandler> messageHandlers) {
        for (MessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
    }

    public SendMessage generateAnswer(Update update){
        String messageText;
        long chatId;
        if(update.hasMessage()) {
            messageText = update.getMessage().getText();
            chatId = update.getMessage().getChatId();
        } else {
            messageText = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        MessageType messageType = MessageType.findMessageHandler(messageText);
        MessageHandler messageHandler = messageHandlerMap.get(messageType);
        SendMessage answer = messageHandler.generateAnswer(update);
        answer.setChatId(chatId);
        return answer;
    }
}

package com.java_school.cinemabot.telegram;

import com.java_school.cinemabot.telegram.callbacks.CallBackType;
import com.java_school.cinemabot.telegram.callbacks.CallbackHandler;
import com.java_school.cinemabot.telegram.handler.DefaultMessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageType;
import org.aspectj.weaver.ast.Call;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageDistributor {
    private final Map<MessageType, MessageHandler> messageHandlerMap = new HashMap<>();
    private final Map<CallBackType, CallbackHandler> callbackHandlerMap = new HashMap<>();

    public MessageDistributor(List<MessageHandler> messageHandlers,
                              List<CallbackHandler> callbackHandlers) {
        for (MessageHandler handler : messageHandlers) {
            messageHandlerMap.put(handler.getMessageType(), handler);
        }
        for (CallbackHandler handler : callbackHandlers) {
            callbackHandlerMap.put(handler.getCallbackType(), handler);
        }
    }

    public String generateAnswer(Update update) {
        if (update.hasCallbackQuery()) {
            var data = update.getCallbackQuery().getData();
            CallBackType callbackType = CallBackType.findCallbackType(data);
            CallbackHandler callbackHandler = callbackHandlerMap.get(callbackType);
            return callbackHandler.generateAnswer(update);
        } else {
            String messageText = update.getMessage().getText();
            MessageType messageType = MessageType.findMessageHandler(messageText);
            MessageHandler messageHandler = messageHandlerMap.get(messageType);
            return messageHandler.generateAnswer(update);
        }
    }
}

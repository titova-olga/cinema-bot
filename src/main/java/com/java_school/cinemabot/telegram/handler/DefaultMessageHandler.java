package com.java_school.cinemabot.telegram.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultMessageHandler implements MessageHandler {
    @Override
    public String generateAnswer(Update update) {
        return "I don't recognize the command :(";
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.DEFAULT;
    }
}

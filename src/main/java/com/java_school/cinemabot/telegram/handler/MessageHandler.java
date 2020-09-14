package com.java_school.cinemabot.telegram.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    String generateAnswer(Update update);
    MessageType getMessageType();
}

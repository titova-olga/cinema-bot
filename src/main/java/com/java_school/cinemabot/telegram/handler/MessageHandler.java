package com.java_school.cinemabot.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    SendMessage generateAnswer(Update update);
    MessageType getMessageType();
}

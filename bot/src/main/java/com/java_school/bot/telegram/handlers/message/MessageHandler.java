package com.java_school.bot.telegram.handlers.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    MessageType getMessageType();
    SendMessage generateAnswer(Update update);
}

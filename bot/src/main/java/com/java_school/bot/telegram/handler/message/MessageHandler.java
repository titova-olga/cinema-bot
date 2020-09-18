package com.java_school.bot.telegram.handler.message;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface MessageHandler {
    MessageType getMessageType();
    SendMessage generateMessage(Update update);
    default boolean isGeneratingNewMessage(Update update) {
        return true;
    }
    default EditMessageText editMessage(Update update) {
        return null;
    }
}

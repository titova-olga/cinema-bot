package com.java_school.bot.telegram.handler.calendar;


import com.java_school.bot.telegram.handler.message.MessageHandler;
import com.java_school.bot.telegram.handler.message.MessageType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CalendarIgnoreMessageHandler implements MessageHandler {
    @Override
    public SendMessage generateAnswer(Update update) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CALENDAR_IGNORE;
    }
}
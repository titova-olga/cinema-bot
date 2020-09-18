package com.java_school.bot.telegram.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultMessageHandler implements MessageHandler {
    @Override
    public SendMessage generateMessage(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("Я не распознал твою команду :(");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.DEFAULT;
    }
}

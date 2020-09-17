package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.telegram.handler.message.MessageHandler;
import com.java_school.cinemabot.telegram.handler.message.MessageType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultMessageHandler implements MessageHandler {
    @Override
    public SendMessage generateAnswer(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("I don't recognize the command :(");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.DEFAULT;
    }
}

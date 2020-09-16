package com.java_school.cinemabot.telegram.handler.calendar;

import com.java_school.cinemabot.telegram.handler.MessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageType;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CalendarDayMessageHandler implements MessageHandler {
    @Override
    public SendMessage generateAnswer(Update update) {
        String[] s = update.getCallbackQuery().getData().split(" ");
        int chosenDay = Integer.parseInt(s[4]);

        SendMessage answer = new SendMessage();
        answer.setText("You choose day number " + chosenDay);
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CALENDAR_DAY;
    }
}

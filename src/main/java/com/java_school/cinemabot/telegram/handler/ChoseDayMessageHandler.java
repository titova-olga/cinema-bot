package com.java_school.cinemabot.telegram.handler;

import com.java_school.cinemabot.telegram.handler.calendar.CalendarCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Component
public class ChoseDayMessageHandler implements MessageHandler {
    @Autowired
    private CalendarCreator calendarCreator;

    @Override
    public SendMessage generateAnswer(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("Выберите день:");
        answer.setReplyMarkup(calendarCreator.createCalendar(LocalDate.now()));
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CHOSE_DAY;
    }
}

package com.java_school.bot.telegram.handlers.calendar;

import com.java_school.bot.telegram.handlers.message.MessageHandler;
import com.java_school.bot.telegram.handlers.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Component
public class CalendarNextMonthMessageHandler implements MessageHandler {
    @Autowired
    private CalendarCreator calendarCreator;

    @Override
    public boolean isGeneratingNewMessage(Update update) {
        return false;
    }

    @Override
    public EditMessageText editMessage(Update update) {
        String[] s = update.getCallbackQuery().getData().split(" ");
        int chosenMonth = Integer.parseInt(s[2]);
        int chosenYear = Integer.parseInt(s[3]);
        var date = LocalDate.of(chosenYear, chosenMonth, 1).plusMonths(1);

        EditMessageText answer = new EditMessageText();
        answer.setText("Выберите дату:");
        answer.setReplyMarkup(calendarCreator.createCalendar(date));
        return answer;
    }

    @Override
    public SendMessage generateMessage(Update update) {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CALENDAR_NEXT;
    }
}
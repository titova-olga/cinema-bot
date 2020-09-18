package com.java_school.bot.telegram.handler.calendar;

import com.java_school.bot.telegram.cache.UsersChoicesCache;
import com.java_school.bot.telegram.handler.message.MessageHandler;
import com.java_school.bot.telegram.handler.message.MessageType;
import com.java_school.bot.telegram.handler.message.Stickers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;

@Component
public class CalendarDayMessageHandler implements MessageHandler {

    @Autowired
    private UsersChoicesCache usersChoicesCache;

    @Override
    public SendMessage generateAnswer(Update update) {
        SendMessage answer = new SendMessage();

        String[] response = update.getCallbackQuery().getData().split(" ");
        int chosenMonth = Integer.parseInt(response[2]);
        int chosenYear = Integer.parseInt(response[3]);
        int chosenDay = Integer.parseInt(response[4]);

        LocalDate chosenDate = LocalDate.of(chosenYear, chosenMonth, chosenDay);
        if(chosenDate.isBefore(LocalDate.now())) {
            answer.setText("На сеансы " + chosenDate + " ты уже опоздал... \nВыбери другую дату" + Stickers.SMILE.getCode());
            return answer;
        }

        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        usersChoicesCache.getOrCreateUserChoice(chatId).addDateChoice(chosenDate);

        answer.setText("Продолжай дальше или посмотри сеансы, основанные на твоем выборе! ");
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CALENDAR_DAY;
    }
}

package com.java_school.bot.telegram.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuMessageHandler implements MessageHandler {
    @Override
    public MessageType getMessageType() {
        return MessageType.MENU;
    }

    @Override
    public SendMessage generateMessage(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("А вот и меню для того, чтобы тебе было удобно подобрать сеансы в кино:");
        answer.setReplyMarkup(createMenuKeyboard());
        return answer;
    }

    public static ReplyKeyboardMarkup createMenuKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("Фильмы"));
        keyboardRow1.add(new KeyboardButton("Кинотеатры"));
        keyboardRow1.add(new KeyboardButton("Даты"));

        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton("Сеансы"));

        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton("Очистить выбор"));

        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);
        keyboard.add(keyboardRow3);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}

package com.java_school.bot.telegram.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartMessageHandler implements MessageHandler {

    @Override
    public SendMessage generateMessage(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("Выбери из меню");
        answer.setReplyMarkup(createMenuKeyboard());
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.START;
    }

    private ReplyKeyboardMarkup createMenuKeyboard() {
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

        keyboard.add(keyboardRow1);
        keyboard.add(keyboardRow2);

        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }
}

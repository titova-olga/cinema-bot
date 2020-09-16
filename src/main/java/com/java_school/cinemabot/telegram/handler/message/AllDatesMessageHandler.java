package com.java_school.cinemabot.telegram.handler.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllDatesMessageHandler implements MessageHandler {

    @Override
    public SendMessage generateAnswer(Update update) {
        SendMessage answer = new SendMessage();
        answer.setText("Выбери день:\n" + "Сегодня /today\n" + "Завтра /tomorrow");
        //answer.setReplyMarkup(generateKeyBoard());
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_DATES;
    }

    /*private InlineKeyboardMarkup generateKeyBoard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Сегодня").setCallbackData("/today"));
        firstRow.add(new InlineKeyboardButton().setText("Завтра").setCallbackData("/tomorrow"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(firstRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }*/
}

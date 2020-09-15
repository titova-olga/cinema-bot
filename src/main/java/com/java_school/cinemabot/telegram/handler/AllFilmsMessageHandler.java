package com.java_school.cinemabot.telegram.handler;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AllFilmsMessageHandler implements MessageHandler {
    @Autowired
    private DatabaseFilmService databaseFilmService;

    @Override
    public SendMessage generateAnswer(Update update) {
        String films = databaseFilmService.getAllFilms().stream().map(Film::getName).collect(Collectors.joining("\n"));
        SendMessage answer = new SendMessage();
        answer.setText(films);
        answer.setReplyMarkup(generateKeyBoard());
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.ALL_FILMS;
    }

    private InlineKeyboardMarkup generateKeyBoard(){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Фильмы").setCallbackData("/films"));

        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        secondRow.add(new InlineKeyboardButton().setText("Тык").setCallbackData("Тык123"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(firstRow);
        rowList.add(secondRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

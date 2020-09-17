package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import com.java_school.cinemabot.telegram.handler.message.MessageHandler;
import com.java_school.cinemabot.telegram.handler.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmMessageHandler implements MessageHandler {

    @Autowired
    private DatabaseFilmService databaseFilmService;

    @Override
    public SendMessage generateAnswer(Update update) {
        int filmId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Film film = databaseFilmService.getFilmById(filmId);
        String filmAnswer = "**" + film.getName() + "**\n" + film.getDescription();
        SendMessage answer = new SendMessage();
        answer.setText(filmAnswer);
        answer.setReplyMarkup(generateKeyBoard(update));
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.FILM;
    }

    private InlineKeyboardMarkup generateKeyBoard(Update update){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Хочу смотреть!").setCallbackData("/answer" + update.getMessage().getText()));
        //firstRow.add(new InlineKeyboardButton().setText("Дата").setCallbackData("/reject"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(firstRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class CinemaMessageHandler implements MessageHandler {
    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @Override
    public SendMessage generateAnswer(Update update) {
        int cinemaId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Cinema cinema = databaseCinemaService.getCinemaById(cinemaId);
        String filmAnswer = Stickers.CINEMA + "<b><i>" + cinema.getName() + "</i></b>" + "\n"
                + cinema.getAddress();

        SendMessage answer = new SendMessage();
        answer.setText(filmAnswer);
        answer.enableHtml(true);
        answer.setReplyMarkup(generateKeyBoard(update));
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.CINEMA;
    }

    private InlineKeyboardMarkup generateKeyBoard(Update update){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Подходит, пойду туда!").setCallbackData("/answer" + update.getMessage().getText()));
        //firstRow.add(new InlineKeyboardButton().setText("Дата").setCallbackData("/reject"));

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(firstRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

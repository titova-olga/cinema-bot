package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class FilmMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public SendMessage generateAnswer(Update update) {
        String filmAnswer = "";
        int filmId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Film film = restTemplate.getForObject(RestUrls.FILMS + "/" + filmId, Film.class);
        if(film != null){
            filmAnswer = Stickers.FILM + "<b><i>" + film.getName() + "</i></b>" + Stickers.FILM + "\n\n"
                    + "<b>Жанр:</b> " + film.getGenre() + "\n"
                    + "<b>Режиссер:</b> " + film.getProducer() + "\n"
                    + "<b>Возрастное ограничение:</b> " + film.getMinAge() + "+\n"
                    + "<b>Описание:</b> " + film.getDescription();
        }
        SendMessage answer = new SendMessage();
        answer.setText(filmAnswer);
        answer.enableHtml(true);
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

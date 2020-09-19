package com.java_school.bot.telegram.handlers.message;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
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
public class CinemaMessageHandler implements MessageHandler {

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Override
    public SendMessage generateAnswer(Update update) {
        String cinemaAnswer = "";
        int cinemaId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Cinema cinema = restTemplate.getForObject(RestUrls.CINEMAS + "/" + cinemaId, Cinema.class);
        if(cinema != null){
            cinemaAnswer = Stickers.CINEMA + "<b><i>" + cinema.getName() + "</i></b>" + "\n"
                    + cinema.getAddress();
        }
        SendMessage answer = new SendMessage();
        answer.setText(cinemaAnswer);
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

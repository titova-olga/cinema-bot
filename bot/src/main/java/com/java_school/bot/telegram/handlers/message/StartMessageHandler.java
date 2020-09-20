package com.java_school.bot.telegram.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartMessageHandler implements MessageHandler {

    @Override
    public SendMessage generateMessage(Update update) {
        SendMessage answer = new SendMessage();
        StringBuilder answerText = new StringBuilder("Я - бот кинотеатров. Могу помочь тебе найти билеты в кино.\n");
        answerText.append("В меню есть несколько кнопок, которые помогут тебе подобрать сеансы.\n");
        answerText.append("Выбирай фильмы, кинотеатры и даты, а по кнопке 'Сеансы' получи информацию о расписании. \n");
        answer.setText(answerText.toString());
        answer.setReplyMarkup(MenuMessageHandler.createMenuKeyboard());
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.START;
    }

}

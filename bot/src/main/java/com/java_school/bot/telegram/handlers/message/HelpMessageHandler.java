package com.java_school.bot.telegram.handlers.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class HelpMessageHandler implements MessageHandler {
    @Override
    public MessageType getMessageType() {
        return MessageType.HELP;
    }

    @Override
    public SendMessage generateMessage(Update update) {
        SendMessage answer = new SendMessage();
        StringBuilder answerText = new StringBuilder("Я - бот кинотеатров. Могу помочь тебе найти билеты в кино.\n");
        answerText.append("Напиши мне /menu или /start чтобы начать поиск.\n");
        answerText.append("В меню есть несколько кнопок, которые помогут тебе подобрать сеансы. Выбирай фильмы, кинотеатры и даты, а по кнопке 'Сеансы' получи информацию о расписании. \n");
        answerText.append("А вот и краткое описание их функций:\n");
        answerText.append(Stickers.FILM + " 'Фильмы' - получить список всех фильмов, которые сейчас в прокате\n");
        answerText.append(Stickers.FILM + " 'Кинотеатры' - получить список кинотеатров\n");
        answerText.append(Stickers.FILM + " 'Дата' - выбрать интересующие тебя даты\n");
        answerText.append(Stickers.FILM + " 'Сеансы' - выбрать сеансы, которые были найдены с учетом твоих пожеланий\n");

        answer.setText(answerText.toString());
        answer.enableHtml(true);
        return answer;
    }
}

package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.telegram.cache.UserChoice;
import com.java_school.bot.telegram.cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CinemaChoiceMessageHandler implements MessageHandler {

    @Autowired
    private UsersChoicesCache usersChoicesCache;

    @Override
    public MessageType getMessageType() {
        return MessageType.CINEMA_CHOICE;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        String response = update.getCallbackQuery().getData();//.split("/")[2];
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int choiceId = Integer.parseInt(response.split("/")[2].split("_")[1]);

        UserChoice userChoice = usersChoicesCache.getOrCreateUserChoice(chatId);
        userChoice.addCinemaChoice(choiceId);

        SendMessage answer = new SendMessage();
        answer.setText("Продолжай дальше или посмотри сеансы, основанные на твоем выборе! ");
        return answer;
    }
}

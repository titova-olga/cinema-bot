package com.java_school.bot.telegram.handler.message;

import com.java_school.bot.telegram.cache.ResponseType;
import com.java_school.bot.telegram.cache.UsersResponsesCache;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AnswerHandler implements MessageHandler {
    @Override
    public MessageType getMessageType() {
        return MessageType.ANSWER;
    }

    @Override
    public SendMessage generateAnswer(Update update) {
        String response = update.getCallbackQuery().getData();//.split("/")[2];
        ResponseType responseType = ResponseType.getResponseType(response);
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        int responseId = Integer.parseInt(response.split("/")[2].split("_")[1]);

        UsersResponsesCache.addUserResponse(chatId, responseType, responseId);

        SendMessage answer = new SendMessage();
        answer.setText("Продолжай дальше или посмотри сеансы, основанные на твоем выборе! ");
        return answer;
    }
}

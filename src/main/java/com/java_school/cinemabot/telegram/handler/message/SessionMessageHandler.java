package com.java_school.cinemabot.telegram.handler.message;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SessionMessageHandler implements MessageHandler {
    @Autowired
    private DatabaseSessionService databaseSessionService;

    @Override
    public SendMessage generateAnswer(Update update) {
        int sessionId = Integer.parseInt(update.getMessage().getText().split("_")[1]);
        Session session = databaseSessionService.getSessionById(sessionId);
        String buyReference = session.getBuyReference();

        String filmAnswer = "Ура! Держи ссылку на покупку билетов!\n" + buyReference;
        SendMessage answer = new SendMessage();
        answer.setText(filmAnswer);
        return answer;
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.SESSION;
    }

}

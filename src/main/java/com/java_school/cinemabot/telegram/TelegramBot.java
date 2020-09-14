package com.java_school.cinemabot.telegram;

import com.java_school.cinemabot.telegram.handler.DefaultMessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageHandler;
import com.java_school.cinemabot.telegram.handler.MessageType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private  TelegramBotsApi telegramBotsApi;

    @Autowired
    private MessageDistributor messageDistributor;

    @SneakyThrows
    @PostConstruct
    public void registerBot(){
        telegramBotsApi.registerBot(this);
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String botAnswer = messageDistributor.generateAnswer(update);
        sendMessage(message.getChatId().toString(), botAnswer);
    }

    @SneakyThrows
    public synchronized void sendMessage(String chatId, String answer) {
        SendMessage sendMessage = new SendMessage(chatId, answer);
        sendMessage.enableMarkdown(true);
        execute(sendMessage);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}

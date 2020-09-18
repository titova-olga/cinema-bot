package com.java_school.bot.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;

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
        try {
            SendMessage botAnswer = messageDistributor.generateAnswer(update);
            sendMessage(botAnswer);
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            System.out.println("Something wrong, but continue to work");
        }
    }

    @SneakyThrows
    public synchronized void sendMessage(SendMessage answer) {
        execute(answer);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
package com.java_school.cinemabot.telegram;

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
        SendMessage botAnswer = messageDistributor.generateAnswer(update);
        sendMessage(botAnswer);
    }

    @SneakyThrows
    public synchronized void sendMessage(SendMessage answer) {
        answer.enableMarkdown(true);
        execute(answer);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}

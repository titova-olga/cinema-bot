package com.java_school.cinemabot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import javax.annotation.PostConstruct;

//@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Autowired
    private  TelegramBotsApi telegramBotsApi;

    @SneakyThrows
    @PostConstruct
    public void registerBot(){
        telegramBotsApi.registerBot(this);
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageText = message.getText();
        User from = message.getFrom();
        String fullInfo = "From " + from.getFirstName() + " " + from.getLastName() + ": " + messageText;
        System.out.println(fullInfo);
        sendMsg(message.getChatId().toString(), fullInfo);
    }

    @SneakyThrows
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        execute(sendMessage);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}

package com.java_school.bot.telegram;

import com.java_school.bot.telegram.handler.message.MessageType;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import javax.annotation.PostConstruct;

import static java.lang.Math.toIntExact;

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
            if (messageDistributor.isGeneratingNewMessage(update)) {
                SendMessage botAnswer = messageDistributor.generateMessage(update);
                sendMessage(botAnswer);
            } else {
                EditMessageText botAnswer = messageDistributor.editMessage(update);
                long messageId = update.getCallbackQuery().getMessage().getMessageId();
                botAnswer.setMessageId(toIntExact(messageId));
                editMessage(botAnswer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something wrong, but continue to work");
        }
    }

    @SneakyThrows
    public synchronized void sendMessage(SendMessage answer) {
        execute(answer);
    }

    @SneakyThrows
    public synchronized void editMessage(EditMessageText answer) {
        execute(answer);
    }

    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }
}
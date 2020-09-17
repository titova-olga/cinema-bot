package com.java_school.cinemabot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@Configuration
public class TelegramBotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }
}

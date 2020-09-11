package com.java_school.cinemabot;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CinemaBotApplication {

    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }

    @SneakyThrows
    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(CinemaBotApplication.class, args);
    }
}

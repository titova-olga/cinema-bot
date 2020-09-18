package com.java_school.bot;

import com.java_school.bot.constants.RestUrls;
import com.java_school.bot.model.Cinema;
import com.java_school.bot.model.Film;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootApplication
public class BotApplication {

    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(BotApplication.class, args);
        /*ConfigurableApplicationContext context = SpringApplication.run(BotApplication.class, args);
        Film film = context.getBean(RestTemplate.class).getForObject(RestUrls.FILMS + "/" + 9, Film.class);
        if(film != null){
            System.out.println(film.getName() + "**\n" + film.getDescription());
        }*/
    }

}

package com.java_school.cinemabot;

import com.java_school.cinemabot.services.DatabaseFilmServiceImpl;
import com.java_school.cinemabot.model.Film;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

@SpringBootApplication
public class CinemaBotApplication {

    /*@Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }
    */

    @SneakyThrows
    public static void main(String[] args) {
        //ApiContextInitializer.init();
        //SpringApplication.run(CinemaBotApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(CinemaBotApplication.class, args);

        DatabaseFilmServiceImpl filmService = context.getBean(DatabaseFilmServiceImpl.class);

        filmService.saveFilms();

        List<Film> allFilms = filmService.getAllFilms();
        for (Film film : allFilms) {
            System.out.println(film);
        }

        context.close();
    }
}

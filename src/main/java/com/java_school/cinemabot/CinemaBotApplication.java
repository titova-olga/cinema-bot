package com.java_school.cinemabot;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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

        DatabaseFilmService filmService = context.getBean(DatabaseFilmService.class);
        DatabaseCinemaService cinemaService = context.getBean(DatabaseCinemaService.class);
        DatabaseSessionService sessionService = context.getBean(DatabaseSessionService.class);

        filmService.saveFilms();
        cinemaService.saveCinemas();
        sessionService.saveSessions();

        List<Film> allFilms = filmService.getAllFilms();
        for (Film film : allFilms) {
            System.out.println(film);
        }

        context.close();
    }
}

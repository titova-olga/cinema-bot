package com.java_school.cinemabot;

import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class CinemaBotApplication {

    @SneakyThrows
    public static void main(String[] args) {

        // ehcache


        ApiContextInitializer.init();
        //SpringApplication.run(CinemaBotApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(CinemaBotApplication.class, args);

        DatabaseFilmService filmService = context.getBean(DatabaseFilmService.class);
        DatabaseCinemaService cinemaService = context.getBean(DatabaseCinemaService.class);
        DatabaseSessionService sessionService = context.getBean(DatabaseSessionService.class);

        /*filmService.saveFilms(context.getBean(DTOFilmService.class).getFilmsFromWebSites());
        cinemaService.saveCinemas(context.getBean(DTOCinemaService.class).getExistingCinemas());
        sessionService.saveSessions(context.getBean(DTOSessionService.class).getSessions());
*/
        List<Film> allFilms = filmService.getAllFilms();
        for (Film film : allFilms) {
            System.out.println(film);
        }

        context.close();
    }
}

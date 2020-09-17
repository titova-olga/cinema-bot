package com.java_school.cinemabot;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableScheduling
public class CinemaBotApplication {

    @SneakyThrows
    public static void main(String[] args) {

        ApiContextInitializer.init();
        //SpringApplication.run(CinemaBotApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(CinemaBotApplication.class, args);
//
//        DatabaseFilmService filmService = context.getBean(DatabaseFilmService.class);
//        DatabaseCinemaService cinemaService = context.getBean(DatabaseCinemaService.class);
//        DatabaseSessionService sessionService = context.getBean(DatabaseSessionService.class);

        /*filmService.saveFilms(context.getBean(DTOFilmService.class).getFilmsFromWebSites());
        cinemaService.saveCinemas(context.getBean(DTOCinemaService.class).getExistingCinemas());
        sessionService.saveSessions(context.getBean(DTOSessionService.class).getSessions());
*/
    }
}

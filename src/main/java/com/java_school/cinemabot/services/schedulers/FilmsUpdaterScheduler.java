package com.java_school.cinemabot.services.schedulers;

import com.java_school.cinemabot.parsing.CinemaParser;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class FilmsUpdaterScheduler {

    @Autowired
    private DatabaseFilmService databaseFilmService;

    @Autowired
    private CinemaParser cinemaParser;

    @PostConstruct
    public void init(){
        getFilms();
    }

    @Scheduled(cron = "0 0 12 * * *") // s, m, h, week day, month
    public void getFilms() {
        databaseFilmService.saveFilms(cinemaParser.parseFilms());
    }
}

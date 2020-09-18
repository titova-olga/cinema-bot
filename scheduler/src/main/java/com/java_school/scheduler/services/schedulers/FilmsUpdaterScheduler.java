package com.java_school.scheduler.services.schedulers;

import com.java_school.scheduler.parsing.CinemaParser;
import com.java_school.scheduler.services.database.DatabaseFilmService;
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

    @Scheduled(cron = "0 0 12 * * *")
    public void getFilms() {
        databaseFilmService.saveFilms(cinemaParser.parseFilms());
    }
}

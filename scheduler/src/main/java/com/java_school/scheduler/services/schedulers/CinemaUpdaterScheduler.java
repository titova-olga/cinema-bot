package com.java_school.scheduler.services.schedulers;

import com.java_school.scheduler.parsing.CinemaParser;
import com.java_school.scheduler.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class CinemaUpdaterScheduler {
    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @Autowired
    private CinemaParser cinemaParser;

    @PostConstruct
    public void init(){
        getCinemas();
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void getCinemas() {
        databaseCinemaService.saveCinemas(cinemaParser.parseCinemas());
    }
}

package com.java_school.cinemabot.services.schedulers;

import com.java_school.cinemabot.parsing.CinemaParser;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CinemaUpdaterScheduler {
    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @Autowired
    private CinemaParser cinemaParser;

    @Scheduled(cron = "0 0 12 * * *") // s, m, h, week day, month
    public void getCinemas() {
        databaseCinemaService.saveCinemas(cinemaParser.parseCinemas());
    }
}

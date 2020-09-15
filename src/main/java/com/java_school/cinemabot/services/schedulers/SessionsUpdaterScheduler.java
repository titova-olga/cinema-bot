package com.java_school.cinemabot.services.schedulers;

import com.java_school.cinemabot.parsing.CinemaParser;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SessionsUpdaterScheduler {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @Autowired
    private CinemaParser cinemaParser;

    @PostConstruct
    public void init(){
        getSessions();
    }

    @Scheduled(cron = "0 0 12 * * *") // s, m, h, week day, month
    public void getSessions() {
        // todo clear
        databaseSessionService.saveSessions(cinemaParser.parseSessions());
    }
}

package com.java_school.scheduler.services.schedulers;

import com.java_school.scheduler.parsing.CinemaParser;
import com.java_school.scheduler.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

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

    @Scheduled(cron = "0 0 12 * * *")
    public void getSessions() {
        databaseSessionService.deleteAllSessions();

        LocalDate date = LocalDate.now().plusDays(1); // todo parse more than one day
        databaseSessionService.saveSessions(cinemaParser.parseSessions(date));
    }
}

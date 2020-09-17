package com.java_school.cinemabot.services.schedulers;

import com.java_school.cinemabot.parsing.CinemaParser;
import com.java_school.cinemabot.parsing.dto.SessionDTO;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SessionsUpdaterScheduler {

    @Value("${schedule.sessions.days_to_parse}")
    private int daysToParse;

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

        List<SessionDTO> allSessions = IntStream.range(0, daysToParse)
                .mapToObj(i -> cinemaParser.parseSessions(LocalDate.now().plusDays(i)))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        databaseSessionService.saveSessions(allSessions);
    }
}

package com.java_school.cinemabot.services.external.fake_services;

import com.github.javafaker.Faker;
import com.java_school.cinemabot.services.external.DTOSessionService;
import com.java_school.cinemabot.services.external.model.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FakeDTOSessionService implements DTOSessionService {
    private static final long SESSIONS_AMOUNT = 10;

    @Autowired
    private Faker faker;

    @Override
    public List<SessionDTO> getSessions() {
        return Stream.iterate(1, i -> ++i)
                .limit(SESSIONS_AMOUNT)
                .map(this::createRandomSession)
                .collect(Collectors.toList());
    }

    private SessionDTO createRandomSession(int i) {
        return SessionDTO.builder()
                .cinemaName("Каро 11 Охта Молл")
                .filmName(faker.book().title())
                .date(faker.date().future(20, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .time(faker.date().future(24, TimeUnit.HOURS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalTime())
                .build();
    }
}

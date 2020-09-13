package com.java_school.cinemabot.services.external;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FakeExternalSessionService implements ExternalSessionService {
    private static final long SESSIONS_AMOUNT = 10;

    @Autowired
    private Faker faker;

    @Override
    public List<ExternalSession> getSessions() {
        return Stream.iterate(1, i -> ++i)
                .limit(SESSIONS_AMOUNT)
                .map(this::createRandomSession)
                .collect(Collectors.toList());
    }

    private ExternalSession createRandomSession(int i) {
        return ExternalSession.builder()
                .cinemaName("Каро 11 Охта Молл")
                .filmName(faker.book().title())
                .date(faker.date().future(20, TimeUnit.DAYS).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .build();
    }
}

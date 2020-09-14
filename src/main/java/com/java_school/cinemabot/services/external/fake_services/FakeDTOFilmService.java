package com.java_school.cinemabot.services.external.fake_services;

import com.github.javafaker.Faker;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.external.DTOFilmService;
import com.java_school.cinemabot.services.external.model.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Fake external film service generates fake films before we implement real service
 * */
//@Service
public class FakeDTOFilmService implements DTOFilmService {

    private final int FILMS_AMOUNT = 10;

    @Autowired
    private Faker faker;

    private FilmDTO createRandomFilm(int i) {
        return FilmDTO.builder()
                .name(faker.book().title())
                .genre(faker.book().genre())
                .producer(faker.book().author())
                .minAge(faker.number().numberBetween(0, 18))
                .rating(faker.number().numberBetween(0, 5))
                .releaseDate(faker.date().past(100, TimeUnit.DAYS).toInstant()
                                                                            .atZone(ZoneId.systemDefault())
                                                                            .toLocalDate())
                .build();
    }

    @Override
    public List<FilmDTO> getFilmsFromWebSites() {
        return Stream.iterate(1, i -> ++i)
                .limit(FILMS_AMOUNT)
                .map(this::createRandomFilm)
                .collect(Collectors.toList());
    }
}

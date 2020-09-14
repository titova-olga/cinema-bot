package com.java_school.cinemabot.services;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmServiceImpl;
import com.java_school.cinemabot.services.external.fake_services.FakeDTOFilmService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest()
class DatabaseFilmServiceImplTest {
    @Autowired
    private DatabaseFilmServiceImpl databaseFilmService;

    @MockBean
    private FakeDTOFilmService externalFilmService;

    @Test
    void testFilmsSize() {
        given(externalFilmService.getFilmsFromWebSites()).willReturn(
                Stream.iterate(1, i -> ++i)
                        .limit(10)
                        .map(a -> Film.builder().build())
                        .collect(Collectors.toList())
        );

        databaseFilmService.saveFilms(externalFilmService.getFilmsFromWebSites());
        List<Film> films = databaseFilmService.getAllFilms();
        Assert.assertEquals(10, films.size());
    }
}
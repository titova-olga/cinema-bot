package com.java_school.cinemabot.services.database.impl;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.repo.FilmRepo;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import com.java_school.cinemabot.parsing.dto.FilmDTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DatabaseFilmServiceImpl implements DatabaseFilmService {
    @Autowired
    private FilmRepo filmRepo;

    @SneakyThrows
    @Override
    @Transactional
    @Cacheable(value = "allFilmsCache")
    public List<Film> getAllFilms() {
        log.info("Retrieving films");
        return filmRepo.findAll();
    }

    @Override
    public List<Film> getFilmsByGenre(String genre) {
        return filmRepo.findFilmsByGenre(genre);
    }

    @Override
    @Transactional
    public Film getFilmById(int id) {
        return filmRepo.getFilmById(id);
    }

    @Override
    @Transactional
    public void saveFilms(List<FilmDTO> films) {
        //todo: more complicated logic
        for (FilmDTO filmDTO : films) {
            String filmName = filmDTO.getName();
            Film film = filmRepo.getFilmByName(filmName);
            if(film == null) {
                filmRepo.save(Film.builder()
                        .name(filmDTO.getName())
                        .description(filmDTO.getDescription())
                        .genre(filmDTO.getGenre())
                        .minAge(filmDTO.getMinAge())
                        .producer(filmDTO.getProducer())
                        .rating(filmDTO.getRating())
                        .releaseDate(filmDTO.getReleaseDate())
                        .country(filmDTO.getCountry())
                        .build());
            }
        }
    }
}

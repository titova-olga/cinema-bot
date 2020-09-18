package com.java_school.scheduler.services.database.impl;

import com.java_school.scheduler.model.Film;
import com.java_school.scheduler.parsing.dto.FilmDTO;
import com.java_school.scheduler.repo.FilmRepo;
import com.java_school.scheduler.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DatabaseFilmServiceImpl implements DatabaseFilmService {
    @Autowired
    private FilmRepo filmRepo;

    @Override
    public void saveFilms(List<FilmDTO> films) {
        //todo: more complicated logic
        for (FilmDTO filmDTO : films) {
            String filmName = filmDTO.getName();
            Film film = filmRepo.findFilmByName(filmName);
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

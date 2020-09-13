package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.repo.FilmRepo;
import com.java_school.cinemabot.services.external.ExternalFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatabaseFilmServiceImpl implements DatabaseFilmService {
    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private ExternalFilmService externalFilmService;

    @Override
    @Transactional
    public List<Film> getAllFilms() {
        return filmRepo.findAll();
    }

    @Override
    public List<Film> getFilmsByGenre(String genre) {
        return filmRepo.findFilmsByGenre(genre);
    }

    @Override
    @Transactional
    public void saveFilms() {
        List<Film> films = externalFilmService.getFilmsFromWebSites();
        //todo: more complicated logic
        films.stream()
                .filter(film -> filmRepo.getFilmByName(film.getName()) == null)
                .forEach(film -> filmRepo.save(film));
    }
}

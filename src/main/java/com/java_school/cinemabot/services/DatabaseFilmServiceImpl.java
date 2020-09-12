package com.java_school.cinemabot.services;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.repo.FilmRepo;
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
    @Transactional
    public void saveFilms() {
        externalFilmService.getFilmsFromWebSites().forEach(film -> filmRepo.save(film));
    }
}

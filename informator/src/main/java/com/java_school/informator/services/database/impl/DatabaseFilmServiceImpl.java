package com.java_school.informator.services.database.impl;

import com.java_school.informator.model.Film;
import com.java_school.informator.repo.FilmRepo;
import com.java_school.informator.services.database.DatabaseFilmService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DatabaseFilmServiceImpl implements DatabaseFilmService {
    @Autowired
    private FilmRepo filmRepo;

    @SneakyThrows
    @Override
    @Cacheable(value = "allFilmsCache")
    public List<Film> getAllFilms() {
        return filmRepo.findAll();
    }

    @Override
    public List<Film> getFilmsByGenre(String genre) {
        return filmRepo.findFilmsByGenre(genre);
    }

    @Override
    public Film getFilmById(int id) {
        return filmRepo.findById(id).get();
    }
}

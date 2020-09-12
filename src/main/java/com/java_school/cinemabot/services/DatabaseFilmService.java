package com.java_school.cinemabot.services;

import com.java_school.cinemabot.model.Film;

import java.util.List;

public interface DatabaseFilmService {
    List<Film> getAllFilms();

    void saveFilms();
}

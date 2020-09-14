package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Film;

import java.util.List;

public interface DatabaseFilmService {
    List<Film> getAllFilms();
    List<Film> getFilmsByGenre(String genre);
    void saveFilms(List<Film> films);
}

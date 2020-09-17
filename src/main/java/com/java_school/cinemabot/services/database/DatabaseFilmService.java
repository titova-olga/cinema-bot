package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.parsing.dto.FilmDTO;

import java.util.List;

public interface DatabaseFilmService {
    List<Film> getAllFilms();
    List<Film> getFilmsByGenre(String genre);
    Film getFilmById(int id);
    void saveFilms(List<FilmDTO> films);
}

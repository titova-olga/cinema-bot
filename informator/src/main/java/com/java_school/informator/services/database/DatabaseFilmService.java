package com.java_school.informator.services.database;

import com.java_school.informator.model.Film;
import java.util.List;

public interface DatabaseFilmService {
    List<Film> getAllFilms();
    List<Film> getFilmsByGenre(String genre);
    Film getFilmById(int id);
}

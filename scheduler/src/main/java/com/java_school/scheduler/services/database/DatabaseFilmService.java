package com.java_school.scheduler.services.database;

import com.java_school.scheduler.parsing.dto.FilmDTO;
import java.util.List;

public interface DatabaseFilmService {
    void saveFilms(List<FilmDTO> films);
}

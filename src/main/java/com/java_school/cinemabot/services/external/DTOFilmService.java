package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.external.model.FilmDTO;

import java.util.List;

public interface DTOFilmService {
    List<FilmDTO> getFilmsFromWebSites();
}

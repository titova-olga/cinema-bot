package com.java_school.cinemabot.parsing;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.external.model.CinemaDTO;
import com.java_school.cinemabot.services.external.model.FilmDTO;
import com.java_school.cinemabot.services.external.model.SessionDTO;

import java.util.List;

public interface Parser {
    List<FilmDTO> parseFilms(String url);
    List<CinemaDTO> parseCinemas(String url);
    List<SessionDTO> parseSessions(String url);
}

package com.java_school.scheduler.parsing;

import com.java_school.scheduler.parsing.dto.CinemaDTO;
import com.java_school.scheduler.parsing.dto.FilmDTO;
import com.java_school.scheduler.parsing.dto.SessionDTO;

import java.time.LocalDate;
import java.util.List;

public interface CinemaParser {
    List<FilmDTO> parseFilms();
    List<CinemaDTO> parseCinemas();
    List<SessionDTO> parseSessions(LocalDate date);
}

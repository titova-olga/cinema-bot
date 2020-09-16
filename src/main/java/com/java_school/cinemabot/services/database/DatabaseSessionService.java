package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.parsing.dto.SessionDTO;

import java.time.LocalDate;
import java.util.List;

public interface DatabaseSessionService {
    Session getSessionById(int sessionId);
    List<Session> getSessionByCinema(String cinema);
    List<Session> getSessionByFilm(String film);
    List<Session> getSessionByDate(LocalDate date);
    List<Session> getByFilmId(int filmId);
    List<Session> getByFilmAndCinema(int filmId, int cinemaId);
    List<Session> getByFilmAndCinemaAndDate(int filmId, int cinemaId, LocalDate date);
    void deleteAllSessions();
    void saveSessions(List<SessionDTO> sessions);

}


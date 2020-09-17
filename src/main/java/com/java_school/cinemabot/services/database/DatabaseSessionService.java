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
    List<Session> getSessionsByFilmAndCinema(int filmId, int cinemaId);
    List<Session> getSessionsByFilmAndCinemaAndDate(int filmId, int cinemaId, LocalDate date);
    List<Session> getSessionsByFilmId(int filmId);
    List<Session> getSessionsByCinemaId(int cinemaId);
    List<Session> getSessionsByFilmIdsCinemaIdsDatesAndIfAbsentGetAll(List<Integer> filmIds,
                                                                      List<Integer> cinemaIds,
                                                                      List<LocalDate> dates);

    void deleteAllSessions();
    void saveSessions(List<SessionDTO> sessions);
}


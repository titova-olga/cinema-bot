package com.java_school.informator.services.database;

import com.java_school.informator.model.Session;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface DatabaseSessionService {
    Session getSessionById(int sessionId);
    List<Session> getSessionByCinemaName(String cinema);
    List<Session> getSessionByFilmName(String film);
    List<Session> getSessionByDate(LocalDate date);
    List<Session> getSessionsByFilmAndCinema(int filmId, int cinemaId);
    List<Session> getSessionsByFilmAndCinemaAndDate(int filmId, int cinemaId, LocalDate date);
    List<Session> getSessionsByFilmId(int filmId);
    List<Session> getSessionsByCinemaId(int cinemaId);
    List<Session> getSessions(List<Integer> films,List<Integer> cinemas, List<LocalDate> dates);
}


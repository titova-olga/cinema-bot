package com.java_school.informator.services.database.impl;

import com.java_school.informator.model.Session;
import com.java_school.informator.repo.CinemaRepo;
import com.java_school.informator.repo.FilmRepo;
import com.java_school.informator.repo.SessionRepo;
import com.java_school.informator.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DatabaseSessionServiceImpl implements DatabaseSessionService {
    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private FilmRepo filmRepo;

    @Override
    public Session getSessionById(int sessionId) {
        return sessionRepo.findById(sessionId).orElseGet(Session::new);
    }

    @Override
    public List<Session> getSessionByCinemaName(String cinema) {
        return sessionRepo.findByCinemaName(cinema);
    }

    @Override
    public List<Session> getSessionByFilmName(String film) {
        return sessionRepo.findByFilmName(film);
    }

    @Override
    public List<Session> getSessionByDate(LocalDate date) {
        return sessionRepo.findByDate(date);
    }

    @Override
    public List<Session> getSessionsByFilmAndCinema(int filmId, int cinemaId) {
        return sessionRepo.findByFilmIdAndCinemaId(filmId, cinemaId);
    }

    @Override
    public List<Session> getSessionsByFilmAndCinemaAndDate(int filmId, int cinemaId, LocalDate date) {
        return sessionRepo.findByFilmIdAndCinemaIdAndDate(filmId, cinemaId, date);
    }

    @Override
    public List<Session> getSessionsByFilmId(int filmId) {
        return sessionRepo.findByFilmId(filmId);
    }

    @Override
    public List<Session> getSessionsByCinemaId(int cinemaId) {
        return sessionRepo.findByCinemaId(cinemaId);
    }

    @Override
    public List<Session> getSessions(List<Integer> films, List<Integer> cinemas, List<LocalDate> dates) {
        if(dates.isEmpty()){
            dates.add(LocalDate.now());
        }

        /*List<Date> datesTransformed = new ArrayList<>();
        for (LocalDate localDate : dates) {
            datesTransformed.add(java.util.Date.from(localDate.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }
        datesTransformed.forEach(System.out::println);*/
        return sessionRepo.findSessions(films, cinemas, dates);
    }
}

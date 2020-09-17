package com.java_school.cinemabot.services.database.impl;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.repo.CinemaRepo;
import com.java_school.cinemabot.repo.FilmRepo;
import com.java_school.cinemabot.repo.SessionRepo;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import com.java_school.cinemabot.parsing.dto.SessionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
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
    public List<Session> getSessionByCinema(String cinema) {
        return sessionRepo.findByCinemaName(cinema);
    }

    @Override
    public List<Session> getSessionByFilm(String film) {
        return sessionRepo.findByFilmName(film);
    }

    @Override
    @Transactional
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
        return sessionRepo.findByCinemaIdOrderByFilmDescDateAsc(cinemaId);
    }

    @Override
    @Transactional
    public void deleteAllSessions() {
        sessionRepo.deleteAllInBatch();
    }

    @Override
    @Transactional
    public List<Session> getSessionsByFilmIdsCinemaIdsDatesAndIfAbsentGetAll(List<Integer> filmIds,
                                                                             List<Integer> cinemaIds,
                                                                             List<LocalDate> dates) {
        if (filmIds.size() == 0) {
            filmIds = filmRepo.getAllIds();
        }
        if (cinemaIds.size() == 0) {
            cinemaIds = cinemaRepo.getAllIds();
        }
        if (dates.size() == 0) {
            dates = new ArrayList<>();
            dates.add(LocalDate.now());
        }

        return sessionRepo.findByFilmCinemaDateIn(filmIds, cinemaIds, dates);
    }


    @Override
    @Transactional
    public void saveSessions(List<SessionDTO> sessions) {
        //List<ExternalSession> sessions = externalSessionService.getSessions();
        for (SessionDTO session : sessions) {
            // todo: more complicated logic
            Cinema cinema = cinemaRepo.findCinemaByName(session.getCinemaName());
            Film film = filmRepo.findFilmByName(session.getFilmName());
            sessionRepo.save(Session.builder()
                    .film(film)
                    .cinema(cinema)
                    .date(session.getDate())
                    .time(session.getTime())
                    .buyReference(session.getBuyReference())
                    .price(session.getPrice())
                    .build());
        }
    }
}

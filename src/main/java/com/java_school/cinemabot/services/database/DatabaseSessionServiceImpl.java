package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.repo.CinemaRepo;
import com.java_school.cinemabot.repo.FilmRepo;
import com.java_school.cinemabot.repo.SessionRepo;
import com.java_school.cinemabot.services.external.ExternalSession;
import com.java_school.cinemabot.services.external.ExternalSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DatabaseSessionServiceImpl implements DatabaseSessionService {
    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private CinemaRepo cinemaRepo;

    @Autowired
    private FilmRepo filmRepo;

    @Autowired
    private ExternalSessionService externalSessionService;

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
    @Transactional
    public void saveSessions() {
        List<ExternalSession> sessions = externalSessionService.getSessions();
        for (ExternalSession session : sessions) {
            Cinema cinema = cinemaRepo.getCinemaByName(session.getCinemaName());
            Film film = filmRepo.getFilmByName(session.getFilmName());
            sessionRepo.save(Session.builder()
                    .film(film)
                    .cinema(cinema)
                    .date(session.getDate())
                    .time(session.getTime())
                    .build());
        }
    }
}

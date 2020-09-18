package com.java_school.scheduler.services.database.impl;

import com.java_school.scheduler.model.Cinema;
import com.java_school.scheduler.model.Film;
import com.java_school.scheduler.model.Session;
import com.java_school.scheduler.parsing.dto.SessionDTO;
import com.java_school.scheduler.repo.CinemaRepo;
import com.java_school.scheduler.repo.FilmRepo;
import com.java_school.scheduler.repo.SessionRepo;
import com.java_school.scheduler.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    public void deleteAllSessions() {
        sessionRepo.deleteAllInBatch();
    }


    @Override
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

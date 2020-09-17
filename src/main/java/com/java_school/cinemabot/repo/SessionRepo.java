package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepo extends JpaRepository<Session, Integer> {
    List<Session> findByCinemaName(String name);
    List<Session> findByFilmName(String name);
    List<Session> findByDate(LocalDate date);
    List<Session> findByFilmId(int filmId);
    List<Session> findByFilmIdAndCinemaId(int filmId, int cinemaId);
    List<Session> findByFilmIdAndCinemaIdAndDate(int filmId, int cinemaId, LocalDate date);
    List<Session> findByCinemaIdOrderByFilmDescDateAsc(int cinemaId);

    @Query("select s from #{#entityName} s " +
            "where s.film.id in ?1 " +
            "and s.cinema.id in ?2 " +
            "and s.date in ?3" +
            " order by s.date, s.cinema.name, s.film.name, s.time")
    List<Session> findByFilmCinemaDateIn(List<Integer> filmIds,
                                         List<Integer> cinemaIds,
                                         List<LocalDate> dates);
}


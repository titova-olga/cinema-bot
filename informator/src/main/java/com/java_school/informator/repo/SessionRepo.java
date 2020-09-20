package com.java_school.informator.repo;

import com.java_school.informator.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface SessionRepo extends JpaRepository<Session, Integer> {
    List<Session> findByCinemaName(String name);
    List<Session> findByFilmName(String name);
    List<Session> findByDate(LocalDate date);
    List<Session> findByFilmId(int filmId);
    List<Session> findByCinemaId(int cinemaId);
    List<Session> findByFilmIdAndCinemaId(int filmId, int cinemaId);
    List<Session> findByFilmIdAndCinemaIdAndDate(int filmId, int cinemaId, LocalDate date);

    @Query("SELECT s FROM Session s WHERE " +
            "(COALESCE(:films, null) is null or s.film.id IN :films ) AND " +
            "(COALESCE(:cinemas, null) is null or s.cinema.id IN :cinemas ) AND " +
            "((s.date in :dates AND s.date = current_date AND (s.time > current_time))" +
            "OR" +
            "(s.date in :dates AND s.date > current_date))" +
            "ORDER BY s.date, s.cinema.name, s.film.name, s.time ")
    List<Session> findSessions(
            @Param("films")List<Integer> films,
            @Param("cinemas")List<Integer> cinemas,
            @Param("dates") List<LocalDate> dates
    );
}


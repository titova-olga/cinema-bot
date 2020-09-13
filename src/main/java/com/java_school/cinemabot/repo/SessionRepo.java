package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepo extends JpaRepository<Session, Integer> {
    List<Session> findByCinemaName(String name);
    List<Session> findByFilmName(String name);
    List<Session> findByDate(LocalDate date);
}


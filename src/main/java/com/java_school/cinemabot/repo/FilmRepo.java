package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FilmRepo extends JpaRepository<Film, Integer> {
    List<Film> findFilmsByGenre(String genre);
    Film getFilmByName(String name);
    Film getFilmById(Integer id);
}

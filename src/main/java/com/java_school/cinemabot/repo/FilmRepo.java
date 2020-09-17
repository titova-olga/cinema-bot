package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface FilmRepo extends JpaRepository<Film, Integer> {
    List<Film> findFilmsByGenre(String genre);
    Film findFilmByName(String name);
    Film findFilmById(int id);

    @Query("select film.id from #{#entityName} film")
    List<Integer> getAllIds();
}

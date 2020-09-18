package com.java_school.scheduler.repo;

import com.java_school.scheduler.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film, Integer> {
    Film findFilmByName(String name);
}

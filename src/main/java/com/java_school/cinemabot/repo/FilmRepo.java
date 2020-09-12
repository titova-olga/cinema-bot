package com.java_school.cinemabot.repo;

import com.java_school.cinemabot.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FilmRepo extends JpaRepository<Film, Integer> {

}

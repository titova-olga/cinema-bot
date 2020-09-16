package com.java_school.cinemabot.controllers;

import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.services.database.DatabaseFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private DatabaseFilmService databaseFilmService;

    @GetMapping
    public List<Film> getAllFilms() {
        return databaseFilmService.getAllFilms();
    }
}

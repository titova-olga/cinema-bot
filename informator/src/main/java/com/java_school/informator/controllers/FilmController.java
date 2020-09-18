package com.java_school.informator.controllers;

import com.java_school.informator.model.Film;
import com.java_school.informator.services.database.DatabaseFilmService;
import com.java_school.informator.users_choices_cache.UserChoice;
import com.java_school.informator.users_choices_cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id){
        return databaseFilmService.getFilmById(id);
    }
}

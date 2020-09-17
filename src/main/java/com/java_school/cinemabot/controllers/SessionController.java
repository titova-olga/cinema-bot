package com.java_school.cinemabot.controllers;

import com.java_school.cinemabot.model.Session;
import com.java_school.cinemabot.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class SessionController {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @GetMapping("/films/{filmId}/sessions")
    public List<Session> getAllSessionsByFilmId(@PathVariable int filmId){
        return databaseSessionService.getSessionsByFilmId(filmId);
    }

    @GetMapping("/cinemas/{cinemaId}/sessions")
    public List<Session> getAllSessionsByCinemaId(@PathVariable int cinemaId){
        return databaseSessionService.getSessionsByCinemaId(cinemaId);
    }
}

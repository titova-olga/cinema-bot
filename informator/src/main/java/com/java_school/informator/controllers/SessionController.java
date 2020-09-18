package com.java_school.informator.controllers;

import com.java_school.informator.model.Film;
import com.java_school.informator.model.Session;
import com.java_school.informator.services.database.DatabaseSessionService;
import com.java_school.informator.users_choices_cache.UserChoice;
import com.java_school.informator.users_choices_cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class SessionController {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @Autowired
    private UsersChoicesCache usersChoicesCache;

    @GetMapping("/sessions/{id}")
    public Session getSessionById(@PathVariable int id){
        return databaseSessionService.getSessionById(id);
    }

    @GetMapping("/sessions")
    public List<Session> getSessions(@RequestParam long chatId){
        UserChoice userChoice = usersChoicesCache.getUserChoice(chatId);
        if (userChoice == null) {
            return null;
        }
        return databaseSessionService.getSessions(userChoice.getFilmIds(), userChoice.getCinemaIds(), userChoice.getDates());
    }

    @GetMapping("/films/{filmId}/sessions")
    public List<Session> getAllSessionsByFilmId(@PathVariable int filmId){
        return databaseSessionService.getSessionsByFilmId(filmId);
    }

    @GetMapping("/cinemas/{cinemaId}/sessions")
    public List<Session> getAllSessionsByCinemaId(@PathVariable int cinemaId){
        return databaseSessionService.getSessionsByCinemaId(cinemaId);
    }
}

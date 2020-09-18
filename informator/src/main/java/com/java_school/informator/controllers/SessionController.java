package com.java_school.informator.controllers;

import com.java_school.informator.model.Session;
import com.java_school.informator.services.database.DatabaseSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
public class SessionController {

    @Autowired
    private DatabaseSessionService databaseSessionService;

    @GetMapping("/sessions/{id}")
    public Session getSessionById(@PathVariable int id){
        return databaseSessionService.getSessionById(id);
    }

    @GetMapping("/sessions")
    public List<Session> getSessions(
            @RequestParam("film") List<Integer> films,
            @RequestParam("cinema") List<Integer> cinemas,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")List<LocalDate> dates){
        System.out.println("Films: ");
        films.forEach(System.out::println);
        System.out.println("Cinemas: ");
        cinemas.forEach(System.out::println);
        System.out.println("Dates: ");
        dates.forEach(System.out::println);
        return databaseSessionService.getSessions(films, cinemas, dates);
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

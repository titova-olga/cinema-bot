package com.java_school.informator.controllers;

import com.java_school.informator.model.Film;
import com.java_school.informator.model.Session;
import com.java_school.informator.services.database.DatabaseSessionService;
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

    @GetMapping("/sessions/{id}")
    public Session getSessionById(@PathVariable int id){
        return databaseSessionService.getSessionById(id);
    }

    @GetMapping("/sessions")
    public List<Session> getSessions(
            @RequestParam int[] films,
            @RequestParam int[] cinemas,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate[] dates){
        List<Integer> filmsList = Arrays.stream(films).boxed().collect(Collectors.toList());
        List<Integer> cinemasList = Arrays.stream(cinemas).boxed().collect(Collectors.toList());
        List<LocalDate> datesList = Arrays.stream(dates).collect(Collectors.toList());

        System.out.println("Films: ");
        filmsList.forEach(System.out::println);
        System.out.println("Cinemas: ");
        cinemasList.forEach(System.out::println);
        System.out.println("Dates: ");
        datesList.forEach(System.out::println);
        return databaseSessionService.getSessions(filmsList, cinemasList, datesList);
    }

    @GetMapping("/films/{filmId}/sessions")
    public List<Session> getAllSessionsByFilmId(@PathVariable int filmId){
        return databaseSessionService.getSessionsByFilmId(filmId);
    }

    @GetMapping("/cinemas/{cinemaId}/sessions")
    public List<Session> getAllSessionsByCinemaId(@PathVariable int cinemaId){
        return databaseSessionService.getSessionsByCinemaId(cinemaId);
    }

    /*private List<?> convertArrayToList(Object[] array) {
        return
    }*/
}

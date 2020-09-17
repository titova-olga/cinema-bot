package com.java_school.cinemabot.controllers;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @GetMapping
    public List<Cinema> getAllCinemas(){
        return databaseCinemaService.getAllCinemas();
    }
}

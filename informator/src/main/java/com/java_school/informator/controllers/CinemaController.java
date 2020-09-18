package com.java_school.informator.controllers;

import com.java_school.informator.model.Cinema;
import com.java_school.informator.services.database.DatabaseCinemaService;
import com.java_school.informator.users_choices_cache.UserChoice;
import com.java_school.informator.users_choices_cache.UsersChoicesCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinemas")
public class CinemaController {

    @Autowired
    private DatabaseCinemaService databaseCinemaService;

    @GetMapping("/{id}")
    public Cinema getCinemaById(@PathVariable int id){
        return databaseCinemaService.getCinemaById(id);
    }

    @GetMapping
    public List<Cinema> getAllCinemas(){
        return databaseCinemaService.getAllCinemas();
    }
}

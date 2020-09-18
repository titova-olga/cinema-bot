package com.java_school.informator.controllers;

import com.java_school.informator.model.Cinema;
import com.java_school.informator.services.database.DatabaseCinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

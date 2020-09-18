package com.java_school.informator.controllers;

import com.java_school.informator.model.CinemaNetwork;
import com.java_school.informator.services.database.DatabaseCinemaNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/cinemaNetworks")
public class CinemaNetworkController {

    @Autowired
    private DatabaseCinemaNetworkService databaseCinemaNetworkService;

    @GetMapping
    public List<CinemaNetwork> getAllCinemaNetworks(){
        return databaseCinemaNetworkService.getAllNetworks();
    }

}

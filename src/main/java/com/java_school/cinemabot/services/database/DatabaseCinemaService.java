package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.services.external.model.CinemaDTO;

import java.util.List;

public interface DatabaseCinemaService {
    void saveCinemas(List<CinemaDTO> cinemas);
    List<Cinema> getCinemasByNetwork(String network);
    List<Cinema> getAllCinemas();
}

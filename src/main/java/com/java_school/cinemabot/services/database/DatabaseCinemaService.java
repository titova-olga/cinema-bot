package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.Film;
import com.java_school.cinemabot.parsing.dto.CinemaDTO;

import java.util.List;

public interface DatabaseCinemaService {
    void saveCinemas(List<CinemaDTO> cinemas);
    Cinema getCinemaById(int cinemaId);
    List<Cinema> getCinemasByNetwork(String network);
    List<Cinema> getAllCinemas();

}

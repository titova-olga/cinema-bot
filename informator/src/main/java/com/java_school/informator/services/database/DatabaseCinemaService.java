package com.java_school.informator.services.database;
import com.java_school.informator.model.Cinema;

import java.util.List;

public interface DatabaseCinemaService {
    Cinema getCinemaById(int cinemaId);
    List<Cinema> getCinemasByNetwork(String network);
    List<Cinema> getAllCinemas();

}

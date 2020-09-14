package com.java_school.cinemabot.services.database;

import com.java_school.cinemabot.model.Cinema;
import com.java_school.cinemabot.model.CinemaNetwork;

import java.util.List;

public interface DatabaseCinemaNetworkService {
    void saveNetworks(List<CinemaNetwork> networks);
    List<CinemaNetwork> getAllNetworks();
}

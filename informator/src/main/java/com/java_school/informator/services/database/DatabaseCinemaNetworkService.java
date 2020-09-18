package com.java_school.informator.services.database;

import com.java_school.informator.model.CinemaNetwork;

import java.util.List;

public interface DatabaseCinemaNetworkService {
    List<CinemaNetwork> getAllNetworks();
    CinemaNetwork getCinemaNetworkByName(String name);
}

package com.java_school.scheduler.services.database;

import com.java_school.scheduler.model.CinemaNetwork;
import java.util.List;

public interface DatabaseCinemaNetworkService {
    void saveNetworks(List<CinemaNetwork> networks);
}

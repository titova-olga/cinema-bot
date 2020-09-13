package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.model.Cinema;

import java.util.List;

public interface ExternalCinemaService {
    List<ExternalCinema> getExistingCinemas();
}

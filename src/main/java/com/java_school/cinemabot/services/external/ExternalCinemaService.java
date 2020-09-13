package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.services.external.model.ExternalCinema;

import java.util.List;

public interface ExternalCinemaService {
    List<ExternalCinema> getExistingCinemas();
}

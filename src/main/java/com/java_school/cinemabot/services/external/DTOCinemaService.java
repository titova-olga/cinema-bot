package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.services.external.model.CinemaDTO;

import java.util.List;

public interface DTOCinemaService {
    List<CinemaDTO> getExistingCinemas();
}

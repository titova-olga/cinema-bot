package com.java_school.scheduler.services.database;

import com.java_school.scheduler.parsing.dto.CinemaDTO;
import java.util.List;

public interface DatabaseCinemaService {
    void saveCinemas(List<CinemaDTO> cinemas);
}

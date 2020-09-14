package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.services.external.model.SessionDTO;

import java.util.List;

public interface DTOSessionService {
    List<SessionDTO> getSessions();
}

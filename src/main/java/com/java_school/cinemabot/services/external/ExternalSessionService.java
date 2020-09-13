package com.java_school.cinemabot.services.external;

import com.java_school.cinemabot.services.external.model.ExternalSession;

import java.util.List;

public interface ExternalSessionService {
    List<ExternalSession> getSessions();
}

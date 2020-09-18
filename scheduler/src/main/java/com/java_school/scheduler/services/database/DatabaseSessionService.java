package com.java_school.scheduler.services.database;

import com.java_school.scheduler.parsing.dto.SessionDTO;
import java.util.List;

public interface DatabaseSessionService {
    void deleteAllSessions();
    void saveSessions(List<SessionDTO> sessions);
}


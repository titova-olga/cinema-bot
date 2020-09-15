package com.java_school.cinemabot.services.external.services;

import com.java_school.cinemabot.parsing.Parser;
import com.java_school.cinemabot.services.external.DTOSessionService;
import com.java_school.cinemabot.services.external.model.SessionDTO;
import org.checkerframework.checker.index.qual.SearchIndexBottom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOSessionServiceImpl implements DTOSessionService {

    @Autowired
    private Parser parser;

    @Override
    public List<SessionDTO> getSessions() {
        return parser.parseSessions();
    }
}

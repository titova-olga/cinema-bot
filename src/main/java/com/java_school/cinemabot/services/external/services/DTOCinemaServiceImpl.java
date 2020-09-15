package com.java_school.cinemabot.services.external.services;

import com.java_school.cinemabot.parsing.Parser;
import com.java_school.cinemabot.services.external.DTOCinemaService;
import com.java_school.cinemabot.services.external.model.CinemaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOCinemaServiceImpl implements DTOCinemaService {

    // todo: list of parsers
    @Autowired
    private Parser parser;

    @Override
    public List<CinemaDTO> getExistingCinemas() {
        return parser.parseCinemas();
    }
}

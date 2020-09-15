package com.java_school.cinemabot.services.external.services;

import com.java_school.cinemabot.parsing.Parser;
import com.java_school.cinemabot.services.external.DTOFilmService;
import com.java_school.cinemabot.services.external.model.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DTOFilmServiceImpl implements DTOFilmService {

    @Autowired
    private Parser parser;

    @Override
    public List<FilmDTO> getFilmsFromWebSites() {
        return parser.parseFilms();
    }
}

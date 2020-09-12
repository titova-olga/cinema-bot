package com.java_school.cinemabot.services;

import com.java_school.cinemabot.model.Film;

import java.util.List;

public interface ExternalFilmService {
    List<Film> getFilmsFromWebSites();
}

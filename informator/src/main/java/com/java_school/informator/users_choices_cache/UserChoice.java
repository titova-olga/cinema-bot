package com.java_school.informator.users_choices_cache;

import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class UserChoice {
    private List<Integer> filmIds = new ArrayList<>();
    private List<Integer> cinemaIds = new ArrayList<>();
    private List<LocalDate> dates = new ArrayList<>();

    public void addFilmChoice(int filmId) {
        filmIds.add(filmId);
    }

    public void addCinemaChoice(int cinemaId) {
        cinemaIds.add(cinemaId);
    }

    public void addDateChoice(LocalDate date) {
        dates.add(date);
    }
}

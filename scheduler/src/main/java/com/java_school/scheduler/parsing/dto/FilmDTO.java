package com.java_school.scheduler.parsing.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class FilmDTO {
    private String name;
    private String genre;
    private int minAge;
    private String description;
    private double rating;
    private String producer;
    private LocalDate releaseDate;
    private String country;
}

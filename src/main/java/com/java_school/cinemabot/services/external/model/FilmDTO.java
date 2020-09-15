package com.java_school.cinemabot.services.external.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

package com.java_school.cinemabot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String genre;
    private int minAge;
    private String description;
    private double rating;
    private String producer;
    private LocalDate releaseDate;
}
